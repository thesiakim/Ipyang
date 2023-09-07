package com.project.ipyang.domain.adopt.dto;

import com.project.ipyang.common.IpyangEnum;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AdoptDto {
    private Long id;
    private String title;
    private IpyangEnum.Status status;
    private Long memberId;
    private String nickname;
    private int view;
    private LocalDateTime createdAt;

    public AdoptDto(Long id, String title, IpyangEnum.Status status, Long memberId,
                    String nickname, int view, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.memberId = memberId;
        this.nickname = nickname;
        this.view = view;
        this.createdAt = createdAt;
    }

}
