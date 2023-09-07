package com.project.ipyang.domain.board.dto;

import com.project.ipyang.domain.board.entity.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class DeleteBoardDto {
    private  Long id;

    public Board toEntity() {
        return Board.builder()
                .id(id)
                .build();
    }
}
