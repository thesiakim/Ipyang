package com.project.ipyang.domain.catType.controller;

import com.project.ipyang.common.response.ResponseDto;
import com.project.ipyang.domain.catType.dto.CatTypeDto;
import com.project.ipyang.domain.catType.dto.WriteCatTypeDto;
import com.project.ipyang.domain.catType.service.CatTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CatTypeController {

    private final CatTypeService catTypeService;

    //품종 등록
    @PostMapping(value = "/v1/admin/cat/register")
    public ResponseDto createCatType(WriteCatTypeDto request) {
        return catTypeService.createCatType(request);
    }

    //품종 조회
    @GetMapping(value = "v1/admin/cat")
    public ResponseDto selectAllCatType() {
        return catTypeService.selectAllCatType();
    }

    //품종 수정
    @PutMapping(value = "v1/admin/cat/edit")
    public ResponseDto updateCatType(CatTypeDto request) {
        return catTypeService.updateCatType(request);
    }

    //품종 삭제
    @DeleteMapping(value = "v1/admin/cat/{id}/delete")
    public ResponseDto deleteCatType(@PathVariable("id") Long id) {
        return catTypeService.deleteCatType(id);
    }
}
