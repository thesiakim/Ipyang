package com.project.ipyang.domain.board.entity;

import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.common.entity.BaseEntity;
import com.project.ipyang.domain.board.dto.SelectCommentDto;
import com.project.ipyang.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")  //댓글아이디
    private Long id;

    @Column(name = "c_content")
    private String content;     //댓글 내용


    @Column(name = "c_likeCnt")
    private int likeCnt;        //댓글따봉수


    @Column(name = "c_group")     //댓글 그룹
    private int ref;

    @Column(name = "c_re_level")
    private int reLevel;        //0이면 댓글 1이면 답글


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")  //작성자
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")  //게시글번호
    private Board board;


    public SelectCommentDto convertSelectDto(){
            return SelectCommentDto.builder()
                    .id(id)
                    .content(content)
                    .likeCnt(likeCnt)
                    .memberId(member.getId())
                    .boardId(board.getId())
                    .createdAt(getCreatedAt())
                    .build();

    }


    public void updateComment(String content) {
        this.content = content;

    }
}
