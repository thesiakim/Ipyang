package com.project.ipyang.domain.apply.dto;

import com.project.ipyang.domain.adopt.entity.Adopt;
import com.project.ipyang.domain.apply.entity.Apply;
import com.project.ipyang.domain.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class WriteApplyDto {
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

    public Apply toEntity(Member member, Adopt adopt) {
        return Apply.builder()
                            .member(member)
                            .adopt(adopt)
                            .name(name)
                            .age(age)
                            .gender(gender)
                            .address(address)
                            .phone(phone)
                            .job(job)
                            .marry(marry)
                            .ask1(ask1)
                            .ask2(ask2)
                            .ask3(ask3)
                            .ask4(ask4)
                            .ask5(ask5)
                            .ask6(ask6)
                            .ask7(ask7)
                            .build();
    }
}
