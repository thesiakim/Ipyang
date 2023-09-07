package com.project.ipyang.domain.apply.entity;

import com.project.ipyang.common.entity.BaseEntity;
import com.project.ipyang.domain.adopt.entity.Adopt;
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
public class Apply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "adopt_id")
    private Adopt adopt;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "a_name")
    private String name;

    @Column(name = "a_age")
    private String age;

    @Column(name = "a_gender")
    private String gender;

    @Column(name = "a_address")
    private String address;

    @Column(name = "a_phone")
    private String phone;

    @Column(name = "a_job")
    private String job;

    @Column(name = "a_marry")
    private String marry;

    /* 입양을 희망하는 이유 */
    @Column(name = "a_ask1")
    private String ask1;

    /* 가족들이 입양에 동의하는지 */
    @Column(name = "a_ask2")
    private String ask2;

    /* 반려 동물을 키워본 적이 있는지 */
    @Column(name = "a_ask3")
    private String ask3;

    /* 과거에 키운 반려 동물의 종류와 수 */
    @Column(name = "a_ask4")
    private String ask4;

    /* 현재 반려 동물을 키우고 있는지 */
    @Column(name = "a_ask5")
    private String ask5;

    /* 현재 키우고 있는 반려 동물의 종류와 수 */
    @Column(name = "a_ask6")
    private String ask6;

    /* 주거의 형태 */
    @Column(name = "a_ask7")
    private String ask7;


}
