package com.project.ipyang.domain.board.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.domain.board.entity.Board;
import com.project.ipyang.domain.board.entity.BoardImg;
import com.project.ipyang.domain.board.repository.LikesRepository;
import com.project.ipyang.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SelectBoardDto {

    private Long id;
    private String title;
    private String content;
    private int viewCnt;
    private long likeCnt;
    private IpyangEnum.BoardCategory category;
    private long memberId;
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private String nickname;
    private List<String> imgList;

    public SelectBoardDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.viewCnt = board.getViewCnt();
        this.category = board.getCategory();
        this.memberId = board.getMember().getId();
        this.createdAt = board.getCreatedAt();
    }


    public SelectBoardDto(Long id, String title, Long memberId, String nickname, long likeCnt, int viewCnt, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.viewCnt = viewCnt;
        this.likeCnt = likeCnt;
        this.memberId = memberId;
        this.nickname = nickname;
        this.createdAt = createdAt;
    }
}