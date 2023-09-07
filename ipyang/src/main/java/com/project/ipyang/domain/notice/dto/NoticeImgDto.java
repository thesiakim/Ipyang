package com.project.ipyang.domain.notice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class NoticeImgDto {
    private Long id;
    private String imgOriginFile;
    private String imgStoredFile;
}
