package com.project.ipyang.domain.catType.dto;

import com.project.ipyang.domain.catType.entity.CatType;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@RequiredArgsConstructor
public class CatTypeDto {
    private Long id;
    private String type;

    public CatTypeDto(CatType catType) {
        this.id = catType.getId();
        this.type = catType.getType();
    }
}
