package com.project.ipyang.domain.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UpdateBoardDto {
    private String title;
    private String content;
}
