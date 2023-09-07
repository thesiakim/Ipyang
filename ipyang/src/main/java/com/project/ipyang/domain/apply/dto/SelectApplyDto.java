package com.project.ipyang.domain.apply.dto;

import com.project.ipyang.domain.apply.entity.Apply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SelectApplyDto {
    private Long memberId;
    private Long adoptId;
    private String name;
    private String age;
    private String gender;
    private String address;
    private String phone;
    private String job;
    private String marry;
    private String ask1;
    private String ask2;
    private String ask3;
    private String ask4;
    private String ask5;
    private String ask6;
    private String ask7;

    public SelectApplyDto(Apply apply) {
        this.memberId = apply.getMember().getId();
        this.adoptId = apply.getAdopt().getId();
        this.name = apply.getName();
        this.age = apply.getAge();
        this.gender = apply.getGender();
        this.address = apply.getAddress();
        this.phone = apply.getPhone();
        this.job = apply.getJob();
        this.marry = apply.getMarry();
        this.ask1 = apply.getAsk1();
        this.ask2 = apply.getAsk2();
        this.ask3 = apply.getAsk3();
        this.ask4 = apply.getAsk4();
        this.ask5 = apply.getAsk5();
        this.ask6 = apply.getAsk6();
        this.ask7 = apply.getAsk7();
    }

}
