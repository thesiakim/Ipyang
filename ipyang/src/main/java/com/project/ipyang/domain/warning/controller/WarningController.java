package com.project.ipyang.domain.warning.controller;

import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.common.response.ResponseDto;
import com.project.ipyang.config.SessionUser;
import com.project.ipyang.domain.warning.dto.AddWordsDto;
import com.project.ipyang.domain.warning.dto.InsertWarningDto;
import com.project.ipyang.domain.warning.entity.BadWords;
import com.project.ipyang.domain.warning.service.WarningService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


@RestController
@RequiredArgsConstructor
@Slf4j
public class WarningController {

    private final WarningService warningService;

    @PostMapping(value = "/v1/warning/{reason}")
    public ResponseDto  createWarning(@PathVariable("reason") IpyangEnum.WarningReason warningReason,
                                      @RequestBody InsertWarningDto request, HttpSession session){

        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");

        Long memberId = loggedInUser.getId();


        return warningService.createWarning(warningReason,request,memberId );

    }


    @PostMapping(value = "/v1/badwords")
    public ResponseDto addBadWords(AddWordsDto addWordsDto, HttpSession session){

        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");

        Long memberId = loggedInUser.getId();

        return warningService.addBadWords(addWordsDto,memberId);
    }



}
