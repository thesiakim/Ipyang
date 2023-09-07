package com.project.ipyang.domain.board.entity;


import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.common.entity.BaseEntity;
import com.project.ipyang.domain.board.dto.BoardDto;
import com.project.ipyang.domain.board.dto.DeleteBoardDto;
import com.project.ipyang.domain.board.dto.SelectBoardDto;
import com.project.ipyang.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board extends BaseEntity {   //공유하기 제보하기  홍보하기
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(name = "b_title")
    private String title;

    @Column(name = "b_content")
    private String content;

    @Column(name = "b_view_cnt")
    private int viewCnt;


    @Column(name = "b_category")
    @Enumerated(EnumType.STRING)
    private IpyangEnum.BoardCategory category;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<BoardImg> boardImgs = new ArrayList<>();


    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();



    public SelectBoardDto convertSelectDto() {
        return SelectBoardDto.builder()
                                     .id(id)
                                     .title(title)
                                     .content(content)
                                     .viewCnt(viewCnt)
                                     .category(category)
                                    .memberId(member.getId())
                                    .createdAt(getCreatedAt())
                                      .nickname(member.getNickname())
                                     .build();
    }



public void UpdateBoard(String title,String content){
 this.title = title;
 this.content = content;
}


    public void updateViewCnt(int viewCnt) {
        this.viewCnt = viewCnt +1;
    }




}
