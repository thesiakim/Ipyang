package com.project.ipyang.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.domain.product.entity.Product;
import com.project.ipyang.domain.product.entity.ProductImg;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto   {
    private Long id;
    private String name;
    private IpyangEnum.Status status;
    private int price;
    private String type;
    private String loc;
    private Long memberId;
    private List<ProductImg> productImgs = new ArrayList<>();
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;




}
