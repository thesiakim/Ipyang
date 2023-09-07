package com.project.ipyang.domain.apply.controller;

import com.project.ipyang.common.response.ResponseDto;
import com.project.ipyang.config.SessionUser;
import com.project.ipyang.domain.apply.dto.WriteApplyDto;
import com.project.ipyang.domain.apply.service.ApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class ApplyController {

    private final ApplyService applyService;

    //입양 신청서 제출
    @PostMapping(value = "/v1/adopt/{id}/apply/submit")
    public ResponseDto submitApply(@PathVariable("id") Long id,
                                   @RequestBody WriteApplyDto request,
                                   HttpSession session) {
        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");
        Long memberId = loggedInUser.getId();

        return applyService.submitApply(id, request, memberId);
    }


}
