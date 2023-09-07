package com.project.ipyang.domain.board.dto;


import com.project.ipyang.domain.board.entity.Board;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class BoardImgDto {
    private Long id;
    private String imgOriginFile;
    private String imgStoredFile;
    private Board board;
}
