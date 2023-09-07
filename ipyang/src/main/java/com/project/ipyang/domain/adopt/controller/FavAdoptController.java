package com.project.ipyang.domain.adopt.controller;

import com.project.ipyang.common.response.ResponseDto;
import com.project.ipyang.config.SessionUser;
import com.project.ipyang.domain.adopt.service.FavAdoptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class FavAdoptController {

    private final FavAdoptService favAdoptService;

    /*
    * 2023-07-05
    * 이후 개발 시 API를 분리할 수도 있기 때문에 보관
    * */

    /*//관심 고양이 등록
    @PostMapping(value = "/v1/adopt/{id}/heart")
    public ResponseDto registerFav(@PathVariable("id") Long id, HttpSession session) {
        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");
        if(loggedInUser == null) return new ResponseDto("로그인이 필요합니다", HttpStatus.INTERNAL_SERVER_ERROR.value());

        Long memberId = loggedInUser.getId();
        if(memberId == null) return new ResponseDto("존재하지 않는 회원입니다", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return favAdoptService.registerFav(id, memberId);
    }


    //관심 고양이 삭제
    @DeleteMapping(value = "/v1/adopt/{id}/heart")
    public ResponseDto removeRegisterFav(@PathVariable("id") Long id, HttpSession session) {
        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");
        if(loggedInUser == null) return new ResponseDto("로그인이 필요합니다", HttpStatus.INTERNAL_SERVER_ERROR.value());

        Long memberId = loggedInUser.getId();
        if(memberId == null) return new ResponseDto("존재하지 않는 회원입니다", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return favAdoptService.removeRegisterFav(id, memberId);

    }*/

    //------------------------------------------------------------------------------------------------------------------

    //관심 고양이 등록 및 삭제
    @PostMapping(value = "/v1/adopt/{id}/heart")
    public ResponseDto favorite(@PathVariable("id") Long id, HttpSession session) {
        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");
        if(loggedInUser == null) return new ResponseDto("로그인이 필요합니다", HttpStatus.INTERNAL_SERVER_ERROR.value());

        Long memberId = loggedInUser.getId();
        if(memberId == null) return new ResponseDto("존재하지 않는 회원입니다", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return favAdoptService.favorite(id, memberId);

    }


}
