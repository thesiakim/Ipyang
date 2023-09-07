package com.project.ipyang.domain.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.domain.board.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SelectCommentDto {
    private Long id;
    private String content;
    private long likeCnt;
    private long memberId;
    private long boardId;
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private String nickname;





    public SelectCommentDto(Comment comment, long likeCnt) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.likeCnt = likeCnt;
        this.memberId = comment.getMember().getId();
        this.nickname = comment.getMember().getNickname();
        this.boardId = comment.getBoard().getId();
        this.createdAt = comment.getCreatedAt();

    }
}
