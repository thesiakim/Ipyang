package com.project.ipyang.domain.vaccine.controller;

import com.project.ipyang.common.response.ResponseDto;
import com.project.ipyang.domain.vaccine.dto.WriteVaccineDto;
import com.project.ipyang.domain.vaccine.dto.VaccineDto;
import com.project.ipyang.domain.vaccine.service.VaccineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class VaccineController {

    private final VaccineService vaccineService;

    //백신 등록
    @PostMapping(value = "/v1/admin/vaccine/register")
    public ResponseDto createVaccine(WriteVaccineDto request) {
        return vaccineService.createVaccine(request);
    }

    //백신 조회
    @GetMapping(value = "/v1/admin/vaccine")
    public ResponseDto selectAllVaccine() {
        return vaccineService.selectAllVaccine();
    }

    //백신수정
    @PutMapping(value = "v1/admin/vaccine/edit")
    public ResponseDto updateVaccine(VaccineDto request) {
        return vaccineService.updateVaccine(request);
    }

    //백신 삭제
    @DeleteMapping(value = "v1/admin/vaccine/{id}/delete")
    public ResponseDto deleteVaccine(@PathVariable("id") Long id) {
        return vaccineService.deleteVaccine(id);
    }
}
