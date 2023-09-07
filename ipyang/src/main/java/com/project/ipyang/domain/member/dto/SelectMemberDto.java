package com.project.ipyang.domain.member.dto;


import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.domain.member.entity.Member;
import lombok.*;

@Setter
@Getter
@ToString(exclude = "passwd")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class SelectMemberDto {
    private String email;
    private String nickname;
    private String passwd;
    private String name;
    private String phone;
    private IpyangEnum.MemberRoleType memberRoleType;
    private String address;
    private IpyangEnum.Status delYn;
    private int point;

    public SelectMemberDto (Member member) {
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.passwd = member.getPasswd();
        this.name = member.getName();
        this.phone = member.getPhone();
        this.memberRoleType = member.getMemberRole();
        this.address = member.getAddress();
        this.delYn = member.getDelYn();
        this.point = member.getPoint();
    }


}
