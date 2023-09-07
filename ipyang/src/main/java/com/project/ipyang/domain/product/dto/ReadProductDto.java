package com.project.ipyang.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.domain.product.entity.ProductImg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ReadProductDto {
    private Long id;
    private String name;
    private String content;
    private IpyangEnum.Status status;
    private int price;
    private IpyangEnum.ProductType type;
    private String loc;
    private Long memberId;
    private String nickname;
    private List<String> imgList;
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
