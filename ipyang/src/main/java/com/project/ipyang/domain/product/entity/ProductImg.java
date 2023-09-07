package com.project.ipyang.domain.product.entity;

import com.project.ipyang.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "product_img")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImg extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "img_id")
    private Long id;

    @Column(name = "img_original_file")
    private String imgOriginFile;

    @Column(name = "img_stored_file")
    private String imgStoredFile;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    public ProductImg(String imgOriginFile, String imgStoredFile, Product product) {
        this.imgOriginFile = imgOriginFile;
        this.imgStoredFile = imgStoredFile;
        this.product = product;

    }
}
