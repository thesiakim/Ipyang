package com.project.ipyang.domain.notice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class NoticeListDto {
    private final Long id;
    private String title;
    private LocalDateTime createdAt;

    public NoticeListDto(Long id, String title, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
    }
}
