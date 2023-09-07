package com.project.ipyang.domain.member.entity;

import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.common.entity.BaseEntity;
import com.project.ipyang.domain.adopt.entity.Adopt;
import com.project.ipyang.domain.adopt.entity.FavAdopt;
import com.project.ipyang.domain.apply.entity.Apply;
import com.project.ipyang.domain.board.entity.Board;
import com.project.ipyang.domain.board.entity.Comment;
import com.project.ipyang.domain.board.entity.Likes;
import com.project.ipyang.domain.inquire.entity.Inquire;
import com.project.ipyang.domain.member.dto.MemberDto;
import com.project.ipyang.domain.notice.entity.Notice;
import com.project.ipyang.domain.point.entity.Point;
import com.project.ipyang.domain.product.entity.Product;
import com.project.ipyang.domain.warning.entity.BadWords;
import com.project.ipyang.domain.warning.entity.Warning;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "m_email")
    private String email;

    @Column(name = "m_nickname")
    private String nickname;

    @Column(name = "m_passwd")
    private String passwd;

    @Column(name = "m_name")
    private String name;

    @Column(name = "m_phone")
    private String phone;

    @Column(name = "m_common_role")
    @Enumerated(EnumType.STRING)
    private IpyangEnum.MemberRoleType memberRole;

    @Column(name = "m_address")
    private String address;

    @Column(name = "m_point")
    private int point;

    @Column(name = "m_delyn")
    @Enumerated(EnumType.STRING)
    private IpyangEnum.Status delYn;  //회원탈퇴여부  y면 y일때 30일뒤에 삭제 n이면 현상태유지

    @Column(name = "m_img_context")
    private String imgContext;

    @Column(name = "m_original_file")
    private String imgOriginFile;

    @Column(name = "m_img_stored_file")
    private String imgStoredFile;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Point> points = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

     @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Board> boards = new ArrayList<>();

     @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Inquire> inquires = new ArrayList<>();

     @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true,fetch = FetchType.LAZY)
     @OrderColumn(name = "notice_order")
    private List<Notice> notices = new ArrayList<>();

     @OneToMany(mappedBy = "member")
     @OrderColumn(name = "warning_order")
    private List<Warning> warnings = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @OrderColumn(name = "adopt_order")
    private List<Adopt> adopts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Apply> applies = new ArrayList<>();

    @OneToMany(mappedBy ="member" )
    private  List<BadWords> words = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<FavAdopt> favAdopts = new ArrayList<>();


    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Likes> likes = new ArrayList<>();


    public MemberDto convertDto(){
        return MemberDto.builder()
                                .id(id)
                                .email(email)
                                .nickname(nickname)
                                .passwd(passwd)
                                .name(name)
                                .phone(phone)
                                .memberRole(memberRole)
                                .address(address)
                                .point(point)
                                .build();
    }


    public void withdraw( ){
        this.delYn = IpyangEnum.Status.Y;
    }

    public void charge(Long pointHistory) {
        this.point += pointHistory;
    }

    public void expense(Long pointHistory) {
        this.point -= pointHistory;
    }

    public void income(Long pointHistory) {
        this.point += pointHistory;
    }
}

