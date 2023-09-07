package com.project.ipyang.domain.adopt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterFavAdoptDto {
    private Long memberId;
    private Long adoptId;
}
