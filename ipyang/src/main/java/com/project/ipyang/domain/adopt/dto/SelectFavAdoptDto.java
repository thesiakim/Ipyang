package com.project.ipyang.domain.adopt.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SelectFavAdoptDto {
    private Long id;
    private Long memberId;
    private Long adoptId;
    private String title;
}
