package com.project.ipyang.domain.member.dto;

import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.domain.board.entity.Board;
import com.project.ipyang.domain.inquire.entity.Inquire;
import com.project.ipyang.domain.member.entity.Member;
import com.project.ipyang.domain.notice.entity.Notice;
import com.project.ipyang.domain.point.entity.Point;
import com.project.ipyang.domain.product.entity.Product;
import com.project.ipyang.domain.warning.entity.Warning;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {
    private Long id;
    private String email;
    private String nickname;
    private String passwd;
    private String name;
    private String phone;
    private IpyangEnum.MemberRoleType memberRole;
    private IpyangEnum.Status delYn;
    private String address;
    private int point;
    private String imgContext;
    private String imgOriginFile;
    private String imgStoredFile;
    private List<Point> points = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private List<Board> boards = new ArrayList<>();
    private List<Inquire> inquires = new ArrayList<>();
    private List<Notice> notices = new ArrayList<>();
    private List<Warning> warnings = new ArrayList<>();

    public Member toEntity(MemberDto dto){
        return Member.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .passwd(dto.getPasswd())
                .name(dto.getName())
                .phone(dto.getPhone())
                .memberRole(dto.getMemberRole())
                .address(dto.getAddress())
                .point(dto.getPoint())
                .build();
    }
    public Member toEntity(){
        return Member.builder()
                .id(id)
                .email(email)
                .nickname(nickname)
                .passwd(passwd)
                .name(name)
                .phone(phone)
                .delYn(delYn)
                .memberRole(memberRole)
                .address(address)
                .point(point)
                .build();
    }


}
