package com.project.ipyang.domain.member.dto;

import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class DeleteMemberDto {
    private Long id;
    private String email;
    private String nickname;
    private String passwd;
    private String name;
    private String phone;
    private IpyangEnum.MemberRoleType memberRole;
    private String address;
    private int point;
    private IpyangEnum.Status delYn;


    public Member toEntity() {
        return Member.builder()
                .id(id)
                .email(email)
                .nickname(nickname)
                .passwd(passwd)
                .name(name)
                .phone(phone)
                .memberRole(memberRole)
                .delYn(delYn)
                .address(address)
                .point(point)
                .build();
    }
}
