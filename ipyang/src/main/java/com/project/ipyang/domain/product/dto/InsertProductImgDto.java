package com.project.ipyang.domain.product.dto;

import lombok.Data;

@Data
public class InsertProductImgDto {

    private String imgOriginFile;
    private String imgStoredFile;
    private Long productId;
}
