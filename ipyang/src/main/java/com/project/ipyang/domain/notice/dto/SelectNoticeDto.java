package com.project.ipyang.domain.notice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.ipyang.common.IpyangEnum;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SelectNoticeDto {
    private Long id;
    private IpyangEnum.NoticeCategory category;
    private String title;
    private String content;
    private List<String> noticeImgs;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public SelectNoticeDto(Long id, IpyangEnum.NoticeCategory category, String title, String content) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.content = content;
    }
}
