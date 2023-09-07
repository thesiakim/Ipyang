package com.project.ipyang.domain.vaccine.service;

import com.project.ipyang.common.response.ResponseDto;
import com.project.ipyang.domain.vaccine.dto.WriteVaccineDto;
import com.project.ipyang.domain.vaccine.dto.VaccineDto;
import com.project.ipyang.domain.vaccine.entity.Vaccine;
import com.project.ipyang.domain.vaccine.repository.VaccineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VaccineService {

    private final VaccineRepository vaccineRepository;

    //백신 등록
    @Transactional
    public ResponseDto createVaccine(WriteVaccineDto request) {
        Vaccine vaccine = Vaccine.builder().name(request.getName()).build();
        Long savedId = vaccineRepository.save(vaccine).getId();

        if(savedId != null) return new ResponseDto("등록되었습니다", HttpStatus.OK.value());
        else return new ResponseDto("에러가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    //백신 조회
    @Transactional
    public ResponseDto selectAllVaccine() {
        List<Vaccine> vaccines = vaccineRepository.findAll();
        if(!vaccines.isEmpty()) {
            List<VaccineDto> vaccineDtos = vaccines.stream().map(VaccineDto::new).collect(Collectors.toList());
            return new ResponseDto(vaccineDtos, HttpStatus.OK.value());
        } else return new ResponseDto("데이터가 없습니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    //백신 수정
    @Transactional
    public ResponseDto updateVaccine(VaccineDto request) {
        Optional<Vaccine> vaccine = vaccineRepository.findById(request.getId());
        if(vaccine.isPresent()) {
            vaccine.get().update(request.getName());
            return new ResponseDto("수정되었습니다", HttpStatus.OK.value());
        } else return new ResponseDto("존재하지 않는 데이터입니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    //백신 삭제
    @Transactional
    public ResponseDto deleteVaccine(Long id) {
        Optional<Vaccine> vaccine = vaccineRepository.findById(id);
        if(vaccine.isPresent()) {
            vaccineRepository.deleteById(id);
            return new ResponseDto("삭제되었습니다", HttpStatus.OK.value());
        } else return new ResponseDto("존재하지 않는 데이터입니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
