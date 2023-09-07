package com.project.ipyang.domain.notice.entity;

import com.project.ipyang.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "notice_img")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeImg extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "img_id")
    private Long id;

    @Column(name = "img_original_file")
    private String imgOriginFile;

    @Column(name = "img_stored_file")
    private String imgStoredFile;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "notice_id")
    private Notice notice;

    public NoticeImg(String imgOriginFile, String imgStoredFile, Notice notice) {
        this.imgOriginFile = imgOriginFile;
        this.imgStoredFile = imgStoredFile;
        this.notice = notice;
    }
}
