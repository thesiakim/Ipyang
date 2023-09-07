package com.project.ipyang.domain.product.dto;

import com.project.ipyang.domain.adopt.dto.AdoptDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Setter
@Getter
@NoArgsConstructor
public class ProductPageDto {

    private Page<SelectProductDto>  productDtos;
    private int startPage;
    private int endPage;

    public ProductPageDto(Page<SelectProductDto> selectProductDtos, int startPage, int endPage) {
       this.productDtos = selectProductDtos;
        this.startPage = startPage;
        this.endPage = endPage;
    }
}
