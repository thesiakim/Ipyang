package com.project.ipyang.domain.catType.service;

import com.project.ipyang.common.response.ResponseDto;
import com.project.ipyang.domain.catType.dto.CatTypeDto;
import com.project.ipyang.domain.catType.dto.WriteCatTypeDto;
import com.project.ipyang.domain.catType.entity.CatType;
import com.project.ipyang.domain.catType.repository.CatTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatTypeService {

    private final CatTypeRepository catTypeRepository;

    //품종 등록
    @Transactional
    public ResponseDto createCatType(WriteCatTypeDto request) {
        CatType catType = CatType.builder()
                                        .type(request.getType())
                                        .build();
        Long savedId = catTypeRepository.save(catType).getId();
        if(savedId != null) return new ResponseDto("품종이 등록되었습니다", HttpStatus.OK.value());
        else return new ResponseDto("에러가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }


    //품종 수정
    @Transactional
    public ResponseDto updateCatType(CatTypeDto request) {
        Optional<CatType> catType = catTypeRepository.findById(request.getId());
        if(!catType.isPresent()) {
            return new ResponseDto("존재하지 않는 데이터입니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        catType.get().updatetype(request.getType());
        return new ResponseDto("수정되었습니다", HttpStatus.OK.value());
    }


    //품종 삭제
    @Transactional
    public ResponseDto deleteCatType(Long id) {
        Optional<CatType> catType = catTypeRepository.findById(id);

        if(catType.isPresent()) {
            catTypeRepository.deleteById(id);
            return new ResponseDto("삭제되었습니다", HttpStatus.OK.value());
        } else return new ResponseDto("존재하지 않는 데이터입니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }


    //품종 조회
    public ResponseDto selectAllCatType() {
        List<CatType> catTypes = catTypeRepository.findAll();

        if(!catTypes.isEmpty()) {
            List<CatTypeDto> catTypeDtos = catTypes.stream().map(CatTypeDto::new).collect(Collectors.toList());
            return new ResponseDto(catTypeDtos, HttpStatus.OK.value());
        } else return new ResponseDto("데이터가 없습니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
