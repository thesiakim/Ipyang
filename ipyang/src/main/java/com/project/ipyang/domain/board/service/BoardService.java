package com.project.ipyang.domain.board.service;

import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.common.response.ResponseDto;
import com.project.ipyang.common.util.S3Utils;
import com.project.ipyang.config.SessionUser;
import com.project.ipyang.domain.board.dto.*;
import com.project.ipyang.domain.board.entity.Board;
import com.project.ipyang.domain.board.entity.BoardImg;
import com.project.ipyang.domain.board.entity.Comment;
import com.project.ipyang.domain.board.entity.Likes;
import com.project.ipyang.domain.board.repository.BoardImgRepository;
import com.project.ipyang.domain.board.repository.BoardRepository;
import com.project.ipyang.domain.board.repository.CommentRepository;
import com.project.ipyang.domain.board.repository.LikesRepository;
import com.project.ipyang.domain.member.entity.Member;
import com.project.ipyang.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final BoardImgRepository boardImgRepository;
    private final LikesRepository likesRepository;
    private final HttpSession session;
    private final S3Utils s3Utils;
    @Transactional
    public ResponseDto writeBoard(IpyangEnum.BoardCategory sC, InsertBoardDto boardDto
                                  //MultipartFile file
                                 ) throws IOException {
        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");
        Long memberId = loggedInUser.getId();
        Optional<Member> member = memberRepository.findById(memberId);
        Board writeBoard = null;

        log.info("조건문 들어가기전 어디가 문제일까" );
        log.info("조건문 들어가기전 어디가 문제일까 boardDto.getBoardFile().size()->"+ boardDto.getBoardFile() );
        //사진을 첨부하지아니할경우
        if (boardDto.getBoardFile()== null || boardDto.getBoardFile().isEmpty()) {

            Board board = Board.builder().title(boardDto.getTitle())
                    .content(boardDto.getContent())
                    .category(sC)
                    .member(member.get())
                    .build();
             writeBoard = boardRepository.save(board);
        }
        else {
            log.info(" 이미지가 있는경우 boardDto.getBoardFile().size()->"+  boardDto.getBoardFile().size());

            Board board = Board.builder().title(boardDto.getTitle())
                        .content(boardDto.getContent())
                        .category(sC)
                        .member(member.get())
                        .build();
            writeBoard = boardRepository.save(board);
            Long savedId = writeBoard.getId();
            Board boardId =  boardRepository.findById(savedId).get();

            for (MultipartFile boardFile : boardDto.getBoardFile()) {
                String imgOriginFile = boardFile.getOriginalFilename();
                String imgUrl = s3Utils.uploadFileToS3(boardFile,"board");

                BoardImg boardImg = new BoardImg(imgOriginFile,imgUrl,boardId);
                boardImgRepository.save(boardImg);
            }
        }


        if (writeBoard != null) {
            return new ResponseDto("게시글을 작성했습니다.", HttpStatus.OK.value());
        }else {
            return new ResponseDto("게시글을 작성 에러.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }




    //전체 게시판 게시글 가져오기
    @Transactional
    public ResponseDto selectAllBoard( ) {
        List<Board> boards = boardRepository.findAll();
        List<SelectBoardDto> selectBoardDtos = boards.stream().map(SelectBoardDto::new).collect(Collectors.toList());

        if(!selectBoardDtos.isEmpty()) {
            return new ResponseDto(selectBoardDtos, HttpStatus.OK.value());
        } else return new ResponseDto("가져올 데이터가 없습니다", HttpStatus.INTERNAL_SERVER_ERROR.value());

    }
    //카테고리별 글 조회
    @Transactional
    public ResponseDto<List<SelectBoardDto>> selectSomeBoard(IpyangEnum.BoardCategory sC, Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;
        int blockLimit = 5;


        Page<Board> boards = boardRepository.findByCategory(sC, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));



        if (!boards.isEmpty()){
            Page<SelectBoardDto> boardDtos = boards.map(board ->{
                long likeCnt = likesRepository.countByTargetTypeAndTargetId(IpyangEnum.LikeType.BOARD, board.getId());
                return new SelectBoardDto(
                        board.getId(),
                        board.getTitle(),
                        board.getMember().getId(),
                        board.getMember().getNickname(),
                        likeCnt,
                        board.getViewCnt(),
                        board.getCreatedAt()
                );
            });


            int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
            int endPage = ((startPage + blockLimit - 1) < boardDtos.getTotalPages()) ? startPage + blockLimit - 1 : boardDtos.getTotalPages();

            BoardPageDto boardPage  = new BoardPageDto(boardDtos, startPage, endPage);



            return new ResponseDto(boardPage,HttpStatus.OK.value());

        }else
         return new ResponseDto("글이 없습니다", HttpStatus.INTERNAL_SERVER_ERROR.value());

}
    //선택한 글 조회
    @Transactional
    public ResponseDto<ReadBoardDto> readSomeBoard(Long id) {
        Optional<Board> board = boardRepository.findById(id);
        if (!board.isPresent()){
            return new ResponseDto("존재하지 않는 글입니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        Board findBoard = board.get();
        findBoard.updateViewCnt(findBoard.getViewCnt()); //조회수 증가
        SelectBoardDto readBoard = findBoard.convertSelectDto();
        List<String> imgList = new ArrayList<>();
        for (BoardImg boardImg : findBoard.getBoardImgs()) {
            imgList.add(boardImg.getImgStoredFile());
        }
        if(!imgList.isEmpty()) readBoard.setImgList(imgList);

        List<Comment> comments = commentRepository.findByBoardId(id);

        List<SelectCommentDto> readComment = comments.stream().map(comment -> {
            long likeCnt = likesRepository.countByTargetTypeAndTargetId(IpyangEnum.LikeType.COMMENT, comment.getId());
            return new SelectCommentDto(comment, likeCnt);
        }).collect(Collectors.toList());

        ReadBoardDto readBoardDto = new ReadBoardDto(readBoard, readComment);

        return new ResponseDto(readBoardDto,HttpStatus.OK.value());

    }




    //게시글 수정
    @Transactional
    public ResponseDto updateBoard(Long id,UpdateBoardDto request) {
        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");
        Long memberId = loggedInUser.getId();

        Optional<Board> boardOptional = boardRepository.findById(id);
        if (!boardOptional.isPresent()) {
            return new ResponseDto("존재하지 않는 게시글입니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        Board findBoard = boardOptional.get();

        if (memberId.equals(findBoard.getMember().getId())){
            findBoard.UpdateBoard(request.getTitle(),request.getContent());
            boardRepository.save(findBoard);
            return new ResponseDto("게시물 수정 성공.", HttpStatus.OK.value());
        }
        else {
            return new ResponseDto("게시글 작성자만 수정가능합니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }



    @Transactional
    public ResponseDto deleteBoard(Long id ) {
        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");
        Long memberId = loggedInUser.getId();
        Optional<Board> boardOptional = boardRepository.findById(id);
        if (!boardOptional.isPresent()) {
            return new ResponseDto("존재하지 않는 게시글입니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        Board board = boardOptional.get();

        if (memberId.equals(board.getMember().getId())){
            List<Likes> likes =   likesRepository.findByTargetId(id);
            likesRepository.delete((Likes) likes);
            List<Comment> comments = commentRepository.findByBoardId(id);
            //comments.get().getId();

            boardRepository.delete(board);
            return new ResponseDto("게시물 삭제 성공.", HttpStatus.OK.value());
        }
        else {
            return new ResponseDto("게시글 작성자만 삭제가능합니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }



    public ResponseDto likeBoard(Long id ) {
        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");
        Long memberId = loggedInUser.getId();
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        Optional<Likes> likesOptional = likesRepository.findByTargetTypeAndTargetIdAndMember(IpyangEnum.LikeType.BOARD,id, memberOptional.get());

        if(likesOptional.isPresent()){
            likesRepository.delete(likesOptional.get());
            return new ResponseDto("게시글 좋아요취소 성공.", HttpStatus.OK.value());
        }else {
            Likes likes = Likes.builder()
                    .member(memberOptional.get())
                    .targetType(IpyangEnum.LikeType.BOARD)
                    .targetId(id)
                    .build();
            likesRepository.save(likes);
            return new ResponseDto("게시글 좋아요추가 성공.", HttpStatus.OK.value());
        }
    }

//===================댓글========================================
    @Transactional
    public ResponseDto writeComment(Long boardId,InsertCommentDto request) {
        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");
        Long memberId = loggedInUser.getId();

        Board board = boardRepository.findById(boardId).orElse(null);

        Optional<Member> memberOptional = memberRepository.findById(memberId);

        Comment comment = Comment.builder()
                .content(request.getContent())
                .reLevel(0)
                .member(memberOptional.get())
                .board(board)
                .build();


        Comment wrtieComment = commentRepository.save(comment);
        if (wrtieComment != null){
            return new ResponseDto("댓글을 작성했습니다.", HttpStatus.OK.value());
        }
        else {
                return new ResponseDto("댓글 작성 에러.", HttpStatus.INTERNAL_SERVER_ERROR.value());
               }
    }
    @Transactional
    public ResponseDto updateComment(Long id, UpdateCommentDto request ) {
        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");
        Long memberId = loggedInUser.getId();

        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (!commentOptional.isPresent()){
            return new ResponseDto("존재하지않는 댓글입니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        Comment findComment = commentOptional.get();

        if (memberId.equals(findComment.getMember().getId())){
                findComment.updateComment(request.getContent());
            return new ResponseDto("댓글수정성공.", HttpStatus.OK.value());
        }else {

            return new ResponseDto("댓글 작성자만 수정가능합니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

    }

    @Transactional
    public ResponseDto deleteComment(Long id) {
        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");
        Long memberId = loggedInUser.getId();

        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (!commentOptional.isPresent()){
            return new ResponseDto("존재하지않는 댓글입니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        Comment findComment = commentOptional.get();

        if (memberId.equals(findComment.getMember().getId())){
           findComment.delete();
            return new ResponseDto("댓글삭제성공.", HttpStatus.OK.value());
        }else {

            return new ResponseDto("댓글 작성자만 삭제가능합니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

    }

    public ResponseDto likeComment(Long id) {
        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");
        Long memberId = loggedInUser.getId();
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        Optional<Likes> likesOptional = likesRepository.findByTargetTypeAndTargetIdAndMember(IpyangEnum.LikeType.COMMENT,id, memberOptional.get());
        if(likesOptional.isPresent()){
            likesRepository.delete(likesOptional.get());
            return new ResponseDto("댓글 좋아요취소 성공.", HttpStatus.OK.value());
        }else {
            Likes likes = Likes.builder()
                    .member(memberOptional.get())
                    .targetType(IpyangEnum.LikeType.COMMENT)
                    .targetId(id)
                    .build();
            likesRepository.save(likes);
            return new ResponseDto("댓글 좋아요추가 성공.", HttpStatus.OK.value());
        }
    }
}
//쿠키로 좋아요 사용하는방법  반려사유: 쿠키는 기간제라 30일뒤면 좋아요누른 이력이 사라지기때문이다.
//    public ResponseDto likeBoard(Long id ) {
//        Optional<Board> boardOptional = boardRepository.findById(id);
//        if (!boardOptional.isPresent()) {
//            return new ResponseDto("존재하지 않는 게시글입니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());
//        }
//
//        Board board = boardOptional.get();
//        board.likeBoard(board.getLikeCnt());
//        return new ResponseDto("게시물 좋아요를 눌렀습니다.", HttpStatus.OK.value());
//    }
//
//
//    public ResponseDto cancelLikeBoard(Long id ) {
//        Optional<Board> boardOptional = boardRepository.findById(id);
//        if (!boardOptional.isPresent()) {
//            return new ResponseDto("존재하지 않는 게시글입니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());
//        }
//        Board board = boardOptional.get();
//
//        board.cancelLike(board.getLikeCnt());
//        return new ResponseDto("게시물 좋아요를 취소합니다.", HttpStatus.OK.value());
//    }