package com.project.ipyang.domain.product.entity;

import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.common.entity.BaseEntity;
import com.project.ipyang.domain.member.entity.Member;
import com.project.ipyang.domain.product.dto.ReadProductDto;
import com.project.ipyang.domain.product.dto.SelectProductDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "p_name")
    private String name;

    @Column(name = "p_status")//구매가능 판매완료
    @Enumerated(EnumType.STRING)
    private IpyangEnum.Status status;

    @Column(name = "p_price")
    private int price;

    @Column(name = "p_content" )//제퓸설명
    private String content;

    @Column(name = "p_type")//품목
    @Enumerated(EnumType.STRING)
    private IpyangEnum.ProductType type;

    @Column(name = "p_loc")
    private String loc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "product")
    private List<ProductImg> productImgs = new ArrayList<>();

    public SelectProductDto convertDto() {
        return SelectProductDto.builder()
                                        .id(id)
                                        .name(name)
                                        .status(status)
                                        .price(price)
                                        .type(type)
                                        .loc(loc)
                                        .memberId(member.getId())
                                        .createdAt(getCreatedAt())
                                        .build();
    }



    public void soldout(){
        this.status = IpyangEnum.Status.Y;
    }


    public void UpdateProduct(String name, String loc, int price, String content) {

        this.name = name;
        this.loc = loc;
        this.price = price;
        this.content = content;
    }

    public ReadProductDto convertReadDto() {
        return ReadProductDto.builder()
                .id(id)
                .name(name)
                .content(content)
                .status(status)
                .price(price)
                .type(type)
                .loc(loc)
                .memberId(member.getId())
                .nickname(member.getNickname())


                .createdAt(getCreatedAt())
                .build();
    }
}
