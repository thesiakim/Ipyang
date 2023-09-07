package com.project.ipyang.domain.inquire.dto;

import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.domain.inquire.entity.Inquire;
import com.project.ipyang.domain.inquire.entity.InquireImg;
import com.project.ipyang.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class InquireDto {
    private Long id;
    private String title;
    private String content;
    private String passwd;
    private IpyangEnum.Status status;
    private String replyContent;
    private Member member;
    private List<InquireImgDto> inquireImgDtos = new ArrayList<>();

    public Inquire toEntity() {
        List<InquireImg> inquireImgs = inquireImgDtos.stream().map(InquireImgDto::toEntity).collect(Collectors.toList());

        return Inquire.builder()
                .id(id)
                .title(title)
                .content(content)
                .passwd(passwd)
                .status(status)
                .replyContent(replyContent)
                .member(member)
                .inquireImgs(inquireImgs)
                .build();
    }



}
