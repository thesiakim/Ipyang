package com.project.ipyang.domain.product.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ProductImgDto {
    private String imgOriginFile;
    private String imgStoredFile;
    private Long productId;
}
