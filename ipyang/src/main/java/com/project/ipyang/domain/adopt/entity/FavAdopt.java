package com.project.ipyang.domain.adopt.entity;

import com.project.ipyang.common.entity.BaseEntity;
import com.project.ipyang.domain.adopt.dto.SelectFavAdoptDto;
import com.project.ipyang.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "fav_adopt")
public class FavAdopt extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adopt_id")
    private Adopt adopt;

    public FavAdopt(Member member, Adopt adopt) {
        this.adopt = adopt;
        this.member = member;
    }


    //FavAdopt 조회 시 가독성을 위해 재정의
    @Override
    public String toString() {
        return "FavAdopt { id = " + id +
                ", memberId = " + member.getId() +
                ", adoptId = " + adopt.getId() +
                " } ";
    }


    //Entity → DTO
    public SelectFavAdoptDto convertSelectDto(Long favId, Long memberId, Long adoptId, String title) {
        return new SelectFavAdoptDto().builder()
                                                .id(favId)
                                                .memberId(memberId)
                                                .adoptId(adoptId)
                                                .title(title)
                                                .build();

    }


    //관심글 삭제
    public void cancelFavAdopt() {
        member.getFavAdopts().remove(this);
        adopt.getFavAdopts().remove(this);
        this.member = null;
        this.adopt = null;
    }

}
