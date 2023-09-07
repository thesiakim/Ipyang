package com.project.ipyang.domain.inquire.controller;

import com.project.ipyang.common.response.ResponseDto;
import com.project.ipyang.domain.inquire.dto.ReplyContentDto;
import com.project.ipyang.domain.inquire.dto.UpdateInquireDto;
import com.project.ipyang.domain.inquire.dto.WriteInquireDto;
import com.project.ipyang.domain.inquire.dto.SelectInquireDto;
import com.project.ipyang.domain.inquire.service.InquireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@Slf4j
public class InquireController {

    private final InquireService inquireService;
    private final PasswordEncoder passwordEncoder;

    //문의글 작성
    @PostMapping(value = "/v1/inquire/write",
                 consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto createInquire(@ModelAttribute @Valid WriteInquireDto request,
                                     BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors()){
            log.info("result : {}",bindingResult);
            return new ResponseDto("게시글 작성 에러 발생", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        
        return inquireService.createInquire(request, passwordEncoder);
    }

    //전체 문의글 조회
    @GetMapping(value = "/v1/inquire")
    public ResponseDto selectAllInquire(@PageableDefault(page = 1) Pageable pageable) {
        return inquireService.selectAllInquire(pageable);
    }



    //특정 문의글 조회
    @GetMapping(value = "/v1/inquire/{id}")
    public ResponseDto<SelectInquireDto> inquireDetail(@PathVariable("id") Long id, @RequestParam String inputPasswd) {
        return inquireService.selectInquire(id, inputPasswd);
    }



    //특정 문의글 수정
    @PutMapping(value = "/v1/inquire/{id}/edit",
                consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto updateInquire(@PathVariable("id") Long id, @ModelAttribute @Valid UpdateInquireDto request,
                                     BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors()) {
            log.info("result : {}", bindingResult);
            return new ResponseDto("게시글 수정 에러 발생", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return inquireService.updateInquire(id, request);
    }


    //특정 문의글 삭제
    @DeleteMapping(value = "/v1/inquire/{id}/delete")
    public ResponseDto deleteInquire(@PathVariable("id") Long id, @RequestParam String inputPasswd) {
        return inquireService.deleteInquire(id, inputPasswd);
    }


    //관리자 : 문의글에 답변
    @PutMapping(value = "/v1/inquire/{id}/reply")
    public ResponseDto replyInquire(@PathVariable("id") Long id, @RequestBody ReplyContentDto request) {
        return inquireService.replyInquire(id, request);
    }


    //문의글 검색
    /*
     * searchType
     * ---------------------
     * total    |   전체
     * title    |   제목
     * content  |   내용
     * nickName |   닉네임
     * ---------------------
     * */
    @GetMapping(value = "/v1/inquire/search")
    public ResponseDto searchInquire(@RequestParam String searchKeyword, @RequestParam String searchType,
                                     @PageableDefault(page = 1) Pageable pageable) {
        return inquireService.searchInquire(searchKeyword, searchType, pageable);
    }


}
