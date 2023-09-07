package com.project.ipyang.main.dto;

import com.project.ipyang.domain.adopt.dto.SelectAdoptDto;
import com.project.ipyang.domain.board.dto.SelectBoardDto;
import com.project.ipyang.domain.product.dto.SelectProductDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SelectTotalDto {
    private List<SelectAdoptDto> adoptDtos;
    private List<SelectBoardDto> boardDtos;
    private List<SelectProductDto> productDtos;

}
