package com.project.ipyang.domain.product.dto;

import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.domain.member.entity.Member;
import com.project.ipyang.domain.member.repository.MemberRepository;
import com.project.ipyang.domain.product.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class UpdateProductDto {
    private String name;
    private String content;
    private int price;
    private String loc;





}
