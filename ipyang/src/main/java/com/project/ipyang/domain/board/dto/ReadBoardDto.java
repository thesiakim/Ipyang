package com.project.ipyang.domain.board.dto;

import com.project.ipyang.domain.board.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ReadBoardDto {
    private SelectBoardDto selectBoardDtos;
    private List<SelectCommentDto> commentList;
}
