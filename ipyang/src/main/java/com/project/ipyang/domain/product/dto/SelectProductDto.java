package com.project.ipyang.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.domain.member.entity.Member;
import com.project.ipyang.domain.product.entity.Product;
import com.project.ipyang.domain.product.entity.ProductImg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class SelectProductDto  {
    private Long id;
    private String name;
    private IpyangEnum.Status status;
    private int price;
    private IpyangEnum.ProductType type;
    private String loc;
    private Long memberId;
    private String nickname;
    private List<ProductImg> imageFiles;
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public SelectProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.status = product.getStatus();
        this.price = product.getPrice();
        this.type = product.getType();
        this.loc = product.getLoc();
        this.memberId = product.getMember().getId();
    }

    public SelectProductDto(Long id, String name, IpyangEnum.Status status, int price, IpyangEnum.ProductType type,
                            String loc, Long memberId, String nickname,  LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.price = price;
        this.type = type;
        this.loc = loc;
        this.memberId = memberId;
        this.nickname = nickname;
        this.createdAt = createdAt;
    }
}
