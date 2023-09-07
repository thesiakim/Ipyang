package com.project.ipyang.domain.adopt.entity;

import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.common.entity.BaseEntity;
import com.project.ipyang.domain.adopt.dto.AdoptImgDto;
import com.project.ipyang.domain.adopt.dto.SelectAdoptDto;
import com.project.ipyang.domain.adopt.dto.WriteAdoptDto;
import com.project.ipyang.domain.apply.entity.Apply;
import com.project.ipyang.domain.catType.entity.CatType;
import com.project.ipyang.domain.member.entity.Member;
import com.project.ipyang.domain.vaccine.entity.Vaccine;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Adopt extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adopt_id")
    private Long id;

    @Column(name = "a_title")
    private String title;

    @Column(name = "a_content")
    private String content;

    @Column(name = "a_view_cnt")
    private int viewCnt;

    @Column(name = "a_name")
    private String name;

    @Column(name = "a_gender")
    private String gender;

    @Column(name = "a_weight")
    private String weight;

    @Column(name = "a_age")
    private String age;

    @Column(name = "a_neu")
    private String neu;

    @Column(name = "a_adopted_yn")
    @Enumerated(EnumType.STRING)
    private IpyangEnum.Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaccine_id")
    private Vaccine vaccine;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id")
    private CatType catType;

    @OneToMany(mappedBy = "adopt", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    /*@OrderColumn(name = "adopt_img_order")*/
    private List<AdoptImg> adoptImgs = new ArrayList<>();

    @OneToMany(mappedBy = "adopt")
    private List<Apply> applies = new ArrayList<>();

    @OneToMany(mappedBy = "adopt")
    private List<FavAdopt> favAdopts = new ArrayList<>();



    //------------- Entity → DTO --------------------------------------------------------------------------------

    public SelectAdoptDto convertDto() {
        return SelectAdoptDto.builder()
                                    .id(id)
                                    .title(title)
                                    .content(content)
                                    .viewCnt(viewCnt)
                                    .name(name)
                                    .gender(gender)
                                    .weight(weight)
                                    .gender(gender)
                                    .age(age)
                                    .neu(neu)
                                    .status(status)
                                    .createdAt(getCreatedAt())
                                    .memberId(member.getId())
                                    .vacName(vaccine.getName())
                                    .catType(catType.getType())
                                    .build();
    }



    public List<AdoptImgDto> convertImgDto() {
        if(adoptImgs == null || adoptImgs.isEmpty()) return Collections.emptyList();

        List<AdoptImgDto> adoptImgDtoList = new ArrayList<>();
        AdoptImgDto adoptImgDto = null;

        for(AdoptImg adoptImg : adoptImgs) {
            adoptImgDtoList.add(adoptImgDto.builder()
                                                    .id(adoptImg.getId())
                                                    .imgOriginFile(adoptImg.getImgOriginFile())
                                                    .imgStoredFile(adoptImg.getImgStoredFile())
                                                    .build());
        }
        return adoptImgDtoList;
    }


    //-----------------------------------------------------------------------------------------------------------


    //------------- 업데이트 --------------------------------------------------------------------------------------

    //글 수정
    public void update(String title, String content, String name, String gender, String weight,
                       String age, String neu, IpyangEnum.Status status, Vaccine vaccine, CatType catType) {
        this.title = title;
        this.content = content;
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.age = age;
        this.neu = neu;
        this.status = status;
        this.vaccine = vaccine;
        this.catType = catType;
    }

    //조회수 증가
    public void updateViewCount(int view) {
        this.viewCnt = view + 1;
    }
}
