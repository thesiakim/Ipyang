package com.project.ipyang.domain.notice.dto;

import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.domain.member.entity.Member;
import com.project.ipyang.domain.notice.entity.NoticeImg;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class NoticeDto {
    private Long id;
    private IpyangEnum.NoticeCategory category;
    private String content;
    private Member member;
    private List<NoticeImg> noticeImgs = new ArrayList<>();
}
