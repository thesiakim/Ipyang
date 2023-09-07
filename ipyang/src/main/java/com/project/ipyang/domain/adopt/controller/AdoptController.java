package com.project.ipyang.domain.adopt.controller;

import com.project.ipyang.common.response.ResponseDto;
import com.project.ipyang.domain.adopt.dto.*;
import com.project.ipyang.domain.adopt.service.AdoptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdoptController {

    private final AdoptService adoptService;

    //입양글 작성
    @PostMapping(value = "/v1/adopt/write",
                 consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto createAdopt(@ModelAttribute @Valid WriteAdoptDto request,
                                   BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors()) {
            log.info("result : " + bindingResult);
            return new ResponseDto("게시물 작성 에러", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return adoptService.createAdopt(request);
    }


    //전체 입양글 조회 (페이징)
    /*------------------------------------------------------------
    * adoptDtos.getContent());         | 요청 페이지에 해당하는 글
    * adoptDtos.getTotalElements());   | 전체 글 개수
    * adoptDtos.getNumber());          | DB로 요청한 페이지 번호
    * adoptDtos.getTotalPages());      | 전체 페이지 개수
    * adoptDtos.getSize());            | 한 페이지당 노출되는 글 개수
    * adoptDtos.hasPrevious());        | 이전 페이지 존재 여부
    * adoptDtos.isFirst());            | 첫 페이지 여부
    * adoptDtos.isLast());             | 마지막 페이지 여부
    * ------------------------------------------------------------
    * */
    @GetMapping(value = "/v1/adopt")
    public ResponseDto getAdoptList(@PageableDefault(page = 1) Pageable pageable) {
        return adoptService.getAdoptList(pageable);
    }


    //특정 입양글 조회
    @GetMapping(value = "/v1/adopt/{id}")
    public ResponseDto<SelectAdoptDto> adoptDetail(@PathVariable("id") Long id) {
        return adoptService.selectAdopt(id);
    }


    //입양글 수정
    @PutMapping(value = "/v1/adopt/{id}/edit")
    public ResponseDto updateAdopt(@PathVariable("id") Long id, @RequestBody UpdateAdoptDto request) {
        return adoptService.updateAdopt(id, request);
    }


    //입양글 삭제
    @DeleteMapping(value = "/v1/adopt/{id}/delete")
    public ResponseDto deleteAdopt(@PathVariable("id") Long id) {
        return adoptService.deleteAdopt(id);
    }


    //입양 상태, 고양이 품종, 백신 종류에 따라 필터링 및 검색
    /*
    * searchType
    * ---------------------
    * total    |   전체
    * title    |   제목
    * content  |   내용
    * ---------------------
    * */
    @GetMapping(value = "/v1/adopt/filter")
    public ResponseDto filterAdopt(@RequestParam(required = false) String adopted,
                                   @RequestParam(required = false) List<Long> catIds,
                                   @RequestParam(required = false) List<Long> vacIds,
                                   @RequestParam(required = false) String searchKeyword,
                                   @RequestParam(required = false) String searchType,
                                   @PageableDefault(page = 1) Pageable pageable) {
        return adoptService.filterAdopt(adopted, catIds, vacIds, searchKeyword, searchType, pageable);
    }

}
