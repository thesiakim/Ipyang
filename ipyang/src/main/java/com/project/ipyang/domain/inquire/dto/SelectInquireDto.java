package com.project.ipyang.domain.inquire.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.ipyang.domain.inquire.entity.Inquire;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class SelectInquireDto {
    private Long id;
    private String email;
    private String nickName;
    private String title;
    private String content;
    private String replyContent;
    private List<String> inquireImgs;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public SelectInquireDto(Inquire inquire) {
        this.id = inquire.getId();
        this.email = inquire.getMember().getEmail();
        this.nickName = inquire.getMember().getNickname();
        this.title = inquire.getTitle();
        this.content = inquire.getContent();
        this.createdAt = inquire.getCreatedAt();

        if(inquire.getReplyContent() == null) this.replyContent = "답변이 등록되지 않았습니다";
        else this.replyContent = inquire.getReplyContent();
    }
}
