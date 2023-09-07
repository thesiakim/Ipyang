package com.project.ipyang.domain.warning.entity;

import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.common.entity.BaseEntity;
import com.project.ipyang.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Warning extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warning_id")
    private Long id;


    //신고버튼 누른자 => 신고자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //신고당한사람
    @Column(name = "w_suspect")
    private Long suspect;

    //신고사유
    @Column(name = "w_reason")
    @Enumerated(EnumType.STRING)
    private IpyangEnum.WarningReason reason;


}
