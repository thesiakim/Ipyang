package com.project.ipyang.domain.inquire.service;

import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.common.dto.PageDto;
import com.project.ipyang.common.response.ResponseDto;
import com.project.ipyang.common.util.S3Utils;
import com.project.ipyang.config.SessionUser;
import com.project.ipyang.domain.adopt.repository.InquireImgRepository;
import com.project.ipyang.domain.inquire.dto.*;
import com.project.ipyang.domain.inquire.entity.Inquire;
import com.project.ipyang.domain.inquire.entity.InquireImg;
import com.project.ipyang.domain.inquire.repository.InquireRepository;
import com.project.ipyang.domain.member.entity.Member;
import com.project.ipyang.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InquireService {

    private final InquireRepository inquireRepository;
    private final InquireImgRepository inquireImgRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpSession session;
    private final S3Utils s3Utils;

    //문의글 작성
    @Transactional
    public ResponseDto createInquire(WriteInquireDto request, PasswordEncoder passwordEncoder) {
        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");
        Long memberId = loggedInUser.getId();

        Optional<Member> member = memberRepository.findById(memberId);
        Inquire writeInquire = null;

        //파일 미첨부
        if(request.getInquireFile() == null || request.getInquireFile().isEmpty()) {
            Inquire inquire = Inquire.builder()
                                            .title(request.getTitle())
                                            .content(request.getContent())
                                            .passwd(passwordEncoder.encode(request.getPasswd()))
                                            .status(IpyangEnum.Status.N)
                                            .member(member.get())
                                            .build();
            writeInquire = inquireRepository.save(inquire);

        //파일 첨부
        } else {
            Inquire inquire = Inquire.builder()
                    .title(request.getTitle())
                    .content(request.getContent())
                    .passwd(passwordEncoder.encode(request.getPasswd()))
                    .status(IpyangEnum.Status.N)
                    .member(member.get())
                    .build();
            writeInquire = inquireRepository.save(inquire);

            Long savedId = writeInquire.getId();
            Inquire inquireId = inquireRepository.findById(savedId).get();

            for(MultipartFile inquireFile : request.getInquireFile()) {
                String imgOriginFile = inquireFile.getOriginalFilename();
                String imgUrl = s3Utils.uploadFileToS3(inquireFile, "inquire");

                InquireImg inquireImg = new InquireImg(imgOriginFile, imgUrl, inquireId);
                inquireImgRepository.save(inquireImg);
            }
        }
        if(writeInquire != null) return new ResponseDto("문의글을 작성했습니다", HttpStatus.OK.value());
        else return new ResponseDto("에러가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
    

    //전체 문의글 조회 (페이징)
    @Transactional
    public ResponseDto selectAllInquire(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;   //요청 받은 페이지
        int pageLimit = 10;                        //페이지당 글 개수
        int blockLimit = 5;                        //한번에 5개의 페이지만 노출

        Page<Inquire> inquires = inquireRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        if(!inquires.isEmpty()) {
            Page<InquireListDto> inquireListDtos = inquires.map(inquire -> new InquireListDto(inquire.getId(), inquire.getMember().getEmail(),
                                                                                                inquire.getMember().getNickname(), inquire.getTitle(),
                                                                                                inquire.getStatus(), inquire.getCreatedAt()));

            int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
            int endPage = ((startPage + blockLimit - 1) < inquireListDtos.getTotalPages()) ? startPage + blockLimit - 1 : inquireListDtos.getTotalPages();

            PageDto inquirePage = new PageDto(inquireListDtos, startPage, endPage);

            return new ResponseDto(inquirePage, HttpStatus.OK.value());
        } else return new ResponseDto("글이 없습니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }


    //특정 문의글 조회
    @Transactional
    public ResponseDto<SelectInquireDto> selectInquire(Long id, String inputPasswd) {
        Inquire inquire = inquireRepository.findById(id).orElseThrow(()->new IllegalArgumentException("문의글이 존재하지 않습니다."));

        if(inquire != null && passwordEncoder.matches(inputPasswd, inquire.getPasswd())) {
            SelectInquireDto detailInquire = inquire.convertSelectDto();

            List<String> inquireImgs = new ArrayList<>();

            for(InquireImg inquireImg : inquire.getInquireImgs()) {
                inquireImgs.add(inquireImg.getImgStoredFile());
            }
            if(!inquireImgs.isEmpty()) detailInquire.setInquireImgs(inquireImgs);
            return new ResponseDto(detailInquire, HttpStatus.OK.value());

        } else return new ResponseDto("비밀번호가 일치하지 않습니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }


    //특정 문의글 수정
    @Transactional
    public ResponseDto updateInquire(Long id, UpdateInquireDto request) {
        Optional<Inquire> inquire = inquireRepository.findById(id);
        if (!inquire.isPresent()) {
            return new ResponseDto("존재하지 않는 글입니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        if (passwordEncoder.matches(request.getPasswd(), inquire.get().getPasswd())) {
            //파일 미첨부
            if (request.getInquireFile() == null || request.getInquireFile().isEmpty()) {
                inquire.get().update(request.getTitle(), request.getContent());
            } else {
                inquire.get().update(request.getTitle(), request.getContent());
                inquire.get().deleteImgs();    //기존 파일 제거
                for (MultipartFile newFile : request.getInquireFile()) {
                    String imgOriginFile = newFile.getOriginalFilename();
                    String imgUrl = s3Utils.uploadFileToS3(newFile, "inquire");

                    InquireImg newInquireImg = new InquireImg(imgOriginFile, imgUrl, inquire.get());
                    inquireImgRepository.save(newInquireImg);
                }
                return new ResponseDto("수정되었습니다", HttpStatus.OK.value());
            }
        }
        return new ResponseDto("비밀번호가 일치하지 않습니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }


    //특정 문의글 삭제
    @Transactional
    public ResponseDto deleteInquire(Long id, String inputPasswd) {
        Optional<Inquire> inquire = inquireRepository.findById(id);
        if(!inquire.isPresent()) {
            return new ResponseDto("존재하지 않는 글입니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        if(inquire.get().isPasswordMatch(inputPasswd)) {
            inquireRepository.deleteById(id);
            return new ResponseDto("글이 삭제되었습니다", HttpStatus.OK.value());
        }
        else return new ResponseDto("비밀번호가 일치하지 않습니다", HttpStatus.INTERNAL_SERVER_ERROR.value());

    }


    //관리자 : 문의글에 답변
    @Transactional
    public ResponseDto replyInquire(Long id, ReplyContentDto request) {
        Optional<Inquire> inquire = inquireRepository.findById(id);
        if(!inquire.isPresent()) {
            return new ResponseDto("존재하지 않는 글입니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        inquire.get().replyUpdate(request.getReplyContent());
        if(inquire.get().getStatus() == IpyangEnum.Status.Y) {
            return new ResponseDto("답변이 작성되었습니다", HttpStatus.OK.value());
        } else return new ResponseDto("에러가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }


    //문의글 검색
    @Transactional
    public ResponseDto searchInquire(String searchKeyword, String searchType, Pageable pageable) {
        int page = pageable.getPageNumber() - 1;                        //요청 받은 페이지
        int blockLimit = 5;                                             //한번에 5개의 페이지만 노출
        pageable = PageRequest.of(page, 10, pageable.getSort());   //페이지당 최대 10개의 데이터 노출

        Page<InquireListDto> inquireListDtos = inquireRepository.searchInquire(searchKeyword, searchType, pageable, page);

        if(!inquireListDtos.isEmpty()) {
            int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
            int endPage = ((startPage + blockLimit - 1) < inquireListDtos.getTotalPages()) ? startPage + blockLimit - 1 : inquireListDtos.getTotalPages();
            PageDto inquireSearchPage = new PageDto(inquireListDtos, startPage, endPage);

            return new ResponseDto(inquireSearchPage, HttpStatus.OK.value());
        }
        else return new ResponseDto("글이 존재하지 않습니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
