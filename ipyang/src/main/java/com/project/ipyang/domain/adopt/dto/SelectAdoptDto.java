package com.project.ipyang.domain.adopt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.common.entity.BaseEntity;
import com.project.ipyang.domain.adopt.entity.Adopt;
import com.project.ipyang.domain.member.entity.Member;
import com.project.ipyang.domain.vaccine.entity.Vaccine;
import com.querydsl.core.annotations.QueryProjection;
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
public class SelectAdoptDto {
    private Long id;
    private String title;
    private String content;
    private int viewCnt;
    private String name;
    private String gender;
    private String weight;
    private String age;
    private String neu;
    private IpyangEnum.Status status;
    private List<String> adoptImgs;
    private Long memberId;
    private String vacName;
    private String catType;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public SelectAdoptDto(Adopt adopt) {
        this.id = adopt.getId();
        this.title = adopt.getTitle();
        this.content = adopt.getContent();
        this.viewCnt = adopt.getViewCnt();
        this.name = adopt.getName();
        this.gender = adopt.getGender();
        this.weight = adopt.getWeight();
        this.age = adopt.getAge();
        this.neu = adopt.getNeu();
        this.status = adopt.getStatus();
        this.memberId = adopt.getMember().getId();
        this.vacName = adopt.getVaccine().getName();
        this.catType = adopt.getCatType().getType();
        this.createdAt = adopt.getCreatedAt();
    }

    @QueryProjection
    public SelectAdoptDto(Long id, String title, String content, int viewCnt, String name, String gender,
                          String weight, String age, String neu, IpyangEnum.Status status, Long memberId,
                          String vacName, String catType, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.viewCnt = viewCnt;
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.age = age;
        this.neu = neu;
        this.status = status;
        this.memberId = memberId;
        this.vacName = vacName;
        this.catType = catType;
        this.createdAt = createdAt;
    }
}
