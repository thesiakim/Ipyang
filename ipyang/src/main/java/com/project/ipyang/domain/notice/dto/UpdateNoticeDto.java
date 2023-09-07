package com.project.ipyang.domain.notice.dto;

import com.project.ipyang.common.IpyangEnum;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UpdateNoticeDto {
    private IpyangEnum.NoticeCategory category;
    private String title;
    private String content;
}
