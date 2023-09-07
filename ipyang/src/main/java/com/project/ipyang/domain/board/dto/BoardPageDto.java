package com.project.ipyang.domain.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Setter
@Getter
@NoArgsConstructor
public class BoardPageDto {
    private Page<SelectBoardDto> boardDtos;
    private int startPage;
    private int endPage;

    public BoardPageDto(Page<SelectBoardDto> boardDtos, int startPage, int endPage) {
        this.boardDtos = boardDtos;
        this.startPage = startPage;
        this.endPage = endPage;

    }
}
