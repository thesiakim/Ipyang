package com.project.ipyang.domain.member.controller;

import com.project.ipyang.common.response.ResponseDto;
import com.project.ipyang.config.SessionUser;
import com.project.ipyang.domain.adopt.dto.SelectFavAdoptDto;
import com.project.ipyang.domain.apply.dto.SelectApplyDto;
import com.project.ipyang.domain.inquire.dto.SelectInquireDto;
import com.project.ipyang.domain.member.service.MypageService;
import com.project.ipyang.main.dto.SelectTotalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    //회원이 작성한 문의 내역 조회
    @GetMapping(value = "/v1/mypage/inquire")
    public ResponseDto<List<SelectInquireDto>> selectInquireByMember(HttpSession session) {
        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");
        if(loggedInUser == null) return new ResponseDto("로그인이 필요합니다", HttpStatus.INTERNAL_SERVER_ERROR.value());

        Long memberId = loggedInUser.getId();
        if(memberId == null) return new ResponseDto("존재하지 않는 회원입니다", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return mypageService.selectInquireByMember(memberId);
    }

    //회원이 제출한 입양 신청서 조회
    @GetMapping(value = "/v1/mypage/apply")
    public ResponseDto<List<SelectApplyDto>> selectApplyByMember(HttpSession session) {
        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");
        if(loggedInUser == null) return new ResponseDto("로그인이 필요합니다", HttpStatus.INTERNAL_SERVER_ERROR.value());

        Long memberId = loggedInUser.getId();
        if(memberId == null) return new ResponseDto("존재하지 않는 회원입니다", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return mypageService.selectApplyByMember(memberId);
    }

    //회원이 작성한 글 조회 (Adopt, Board, Product)
    @GetMapping(value = "/v1/mypage/list")
    public ResponseDto<List<SelectTotalDto>> selectTotalByMember(HttpSession session) {
        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");
        if(loggedInUser == null) return new ResponseDto("로그인이 필요합니다", HttpStatus.INTERNAL_SERVER_ERROR.value());

        Long memberId = loggedInUser.getId();
        if(memberId == null) return new ResponseDto("존재하지 않는 회원입니다", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return mypageService.selectTotalByMember(memberId);
    }

    //회원의 관심 고양이 조회
    @GetMapping(value = "/v1/mypage/fav")
    public ResponseDto<List<SelectFavAdoptDto>> selectFavAdoptByMember(HttpSession session) {
        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");
        if(loggedInUser == null) return new ResponseDto("로그인이 필요합니다", HttpStatus.INTERNAL_SERVER_ERROR.value());

        Long memberId = loggedInUser.getId();
        if(memberId == null) return new ResponseDto("존재하지 않는 회원입니다", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return mypageService.selectFavAdoptByMember(memberId);

    }

}
