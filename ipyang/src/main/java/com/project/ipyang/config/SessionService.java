package com.project.ipyang.config;

import com.project.ipyang.common.dto.SessionIdDto;
import com.project.ipyang.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionService {

    @Transactional
    public long validating(HttpSession session) {
        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            throw new RuntimeException("로그인이 필요합니다");        }
        Long memberId = loggedInUser.getId();
        if (memberId == null) {
            throw new RuntimeException("존재하지 않는 회원입니다");
        }
        return memberId;
    }



//    @Transactional
//    public ResponseDto<SessionIdDto> validating(HttpSession session) {
//        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");
//        if (loggedInUser == null) {
//            return new ResponseDto("로그인이 필요합니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
//        }
//        Long memberId = loggedInUser.getId();
//        if (memberId == null) {
//            return new ResponseDto("존재하지않는 회원입니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
//        }
//        SessionIdDto sessionIdDto = new SessionIdDto();
//        sessionIdDto.setMemberId(memberId);
//
//        return new ResponseDto(memberId, HttpStatus.INTERNAL_SERVER_ERROR.value());
//
//    }

}