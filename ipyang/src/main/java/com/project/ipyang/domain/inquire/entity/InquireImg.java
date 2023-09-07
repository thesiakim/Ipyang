package com.project.ipyang.domain.inquire.entity;

import com.project.ipyang.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "inquire_img")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquireImg extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "img_id")
    private Long id;

    @Column(name = "img_original_file")
    private String imgOriginFile;

    @Column(name = "img_stored_file")
    private String imgStoredFile;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "inquire_id")
    private Inquire inquire;

    public InquireImg(String imgOriginFile, String imgStoredFile, Inquire inquire) {
        this.imgOriginFile = imgOriginFile;
        this.imgStoredFile = imgStoredFile;
        this.inquire = inquire;
    }
}
