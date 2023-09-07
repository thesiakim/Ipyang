package com.project.ipyang.domain.board.controller;

import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.common.response.ResponseDto;
import com.project.ipyang.config.SessionService;
import com.project.ipyang.domain.board.dto.*;
import com.project.ipyang.domain.board.service.BoardService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService boardService;
    private final SessionService sessionService;
    private final HttpSession session;

    @PostMapping(value = "/v1/board/{category}/write",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto writeBoard(@PathVariable("category") IpyangEnum.BoardCategory sC,
                                             @ModelAttribute @Valid InsertBoardDto request,
                                            BindingResult bindingResult
    ) throws IOException {

        if(bindingResult.hasErrors()){
            log.info("result : {}",bindingResult);
            return new ResponseDto("게시글을 작성 에러.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return boardService.writeBoard(sC, request);
    }

    @ApiOperation(
            value = "전체게시물 조회"
            , notes = "게시판 전체글을 가져온다")
    @GetMapping(value = "/v1/board")
    public ResponseDto<List<SelectBoardDto>> selectAllBoard( ) {

        return boardService.selectAllBoard();
    }


    @ApiOperation(
            value = "카테고리별 게시글 조회"
            , notes = "제보하기 홍보하기 공유하기별로 조회한다.")
    @GetMapping(value = "/v1/board/{category}")
    public ResponseDto<List<SelectBoardDto>> selectSomeBoard(@PathVariable ("category") IpyangEnum.BoardCategory sC
    ,@PageableDefault(page = 1) Pageable pageable) {
        return boardService.selectSomeBoard(sC,pageable);
    }

    @ApiOperation(
            value = "게시글 조회"
            , notes = "선택한 게시글 클릭시 게시글내용,댓글조회가능하고  조회수가 1증가한다.")
    @GetMapping(value = "/v1/board/read/{id}")
    public ResponseDto <List<ReadBoardDto>> readBoard(@PathVariable("id") Long id) {


        return new ResponseDto(boardService.readSomeBoard(id));
    }


    @ApiOperation(
            value = "게시글 수정"
            , notes = "선택한 게시글 제목과 내용을 수정한다.")
    @PutMapping(value = "/v1/board/{id}")
    public ResponseDto<BoardDto> updateBoard(@PathVariable("id")Long id,@RequestBody UpdateBoardDto request) {
        return boardService.updateBoard(id,request);
    }

    @ApiOperation(
            value = "게시글 삭제"
            , notes = "게시글과 게시글에있는 모든 댓글을 삭제한다")
    @DeleteMapping(value = "/v1/board/{id}")
    public ResponseDto deleteBoard(@PathVariable("id")Long id) {
        return new ResponseDto(boardService.deleteBoard(id));
    }


    @ApiOperation(
            value = "게시글 좋아요버튼"
            , notes = "실행시키면 좋아요테이블에 데이터추가 한번더 실행시 데이터 제거")
    @PostMapping(value = "/v1/board/{id}/like")
    public ResponseDto likeBoard(@PathVariable("id")Long id) {

        return  boardService.likeBoard(id);

    }

    //===========================================================================
    @ApiOperation(
            value = "댓글 작성"
            , notes = "게시글에 댓글을 작성한다")
    @PostMapping(value = "/v1/comment/{id}")
    public ResponseDto writeComment(@PathVariable("id")Long id,@RequestBody InsertCommentDto request) {

        return  boardService.writeComment(id,request);
    }

    @PutMapping(value = "/v1/comment/{id}")
    public ResponseDto updateComment(@PathVariable("id")Long id,@RequestBody UpdateCommentDto request){

        return  boardService.updateComment(id,request);
    }

    @DeleteMapping(value = "/v1/comment/{id}")
    public ResponseDto deleteComment(@PathVariable("id")Long id){

        return boardService.deleteComment(id);
    }



    @ApiOperation(
            value = "댓글 좋아요버튼"
            , notes = "실행시키면 좋아요테이블에 데이터추가 한번더 실행시 데이터 제거")
    @PostMapping(value = "/v1/comment/{id}/like")
    public ResponseDto likeComment(@PathVariable("id")Long id) {

        return  boardService.likeComment(id);

    }



}
/*
    @ApiOperation(
            value = "게시글 좋아요버튼"
            , notes = "실행시키면 좋아요1증가 한번 더 누르면 좋아요 1감소")
    @PutMapping(value = "/v1/board/{id}/like")
    public ResponseDto likeBoard(@PathVariable("id")Long id,HttpSession session,
                                 HttpServletRequest request, HttpServletResponse response){
        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return new ResponseDto("로그인이 필요합니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        Long memberId = loggedInUser.getId();
        if (memberId == null) {
            return new ResponseDto("존재하지않는 회원입니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        String cookieKey = "boardlike" + id;

        // 쿠키에서 해당 게시물이 추천된 적이 있는지 검사합니다.
        Cookie[] cookies = request.getCookies();
        boolean boardLikeChk = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieKey)) {
                    // 이미 추천한 경우, 처리할 내용을 작성합니다.
                    // 추천되어있는 쿠키 삭제
                    // 해당 쿠키 값을 비우기
                    // cookie.setValue("");
                    // 쿠키의 유효시간을 0으로 설정 -> 해당 쿠키를 브라우저에게 삭제하도록 지시하는 것
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    boardLikeChk = true;
                    break;
                }
            }

        }
        if (!boardLikeChk) {
            // 추천 1증가
            boardService.likeBoard(id).getStatus();


            // 쿠키에 해당 게시물이 추천된 것을 기록합니다.
            Cookie cookie = new Cookie(cookieKey, "true");
            cookie.setMaxAge(60 * 60 * 24 * 30); // 쿠키 유효기간 30일로 설정
            response.addCookie(cookie);

            return new ResponseDto("게시물 좋아요를 눌렀습니다.", HttpStatus.OK.value());

        }
        else {
            // 추천 1감소
            boardService.cancelLikeBoard(id).getStatus();
//			log.info("BoardController like updateMinus ->" + updateMinus);
            return new ResponseDto("게시물 좋아요를 취소합니다.", HttpStatus.OK.value());

        }

    }
*/
