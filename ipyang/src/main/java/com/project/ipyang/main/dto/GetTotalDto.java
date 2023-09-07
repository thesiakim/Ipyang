package com.project.ipyang.main.dto;

import com.project.ipyang.domain.adopt.dto.SelectAdoptDto;
import com.project.ipyang.domain.board.dto.SelectBoardDto;
import com.project.ipyang.domain.product.dto.SelectProductDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class GetTotalDto {
    private Page<SelectAdoptDto> adoptDtos;
    private Page<SelectBoardDto> boardDtos;
    private Page<SelectProductDto> productDtos;
}
