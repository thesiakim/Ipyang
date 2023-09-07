package com.project.ipyang.domain.inquire.dto;

import com.project.ipyang.common.IpyangEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class InquireListDto {
    private Long id;
    private String email;
    private String nickName;
    private String title;
    private IpyangEnum.Status status;
    private LocalDateTime createdAt;

    public InquireListDto(Long id, String email, String nickName, String title,
                          IpyangEnum.Status status, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.title = title;
        this.status = status;
        this.createdAt = createdAt;
    }
}
