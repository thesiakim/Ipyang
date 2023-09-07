package com.project.ipyang.domain.board.entity;


import com.project.ipyang.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "board_img")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardImg extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "img_id")
    private Long id;

    @Column(name = "img_original_file")
    private String imgOriginFile;

    @Column(name = "img_stored_file")
    private String imgStoredFile;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id")
    private Board board;

    public BoardImg(String imgOriginFile, String imgStoredFile, Board board) {
        this.imgOriginFile = imgOriginFile;
        this.imgStoredFile = imgStoredFile;
        this.board = board;
    }
}
