package com.project.ipyang.domain.member.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UpdateMemberDto {
    private String passwd;
    private String nickname;
    private String phone;
    private String address;
}
