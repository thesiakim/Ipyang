package com.project.ipyang.config;

import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.domain.member.dto.MemberDto;
import com.project.ipyang.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionUser implements Serializable {
    private Long id;
    private String email;
    private String nickname;
    private String name;
    private String phone;
    private IpyangEnum.MemberRoleType memberRole;
    private String address;
    private int point;

    public SessionUser(MemberDto member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.name = member.getName();
        this.phone = member.getPhone();
        this.memberRole = member.getMemberRole();
        this.address = member.getAddress();
        this.point = member.getPoint();
    }



}
