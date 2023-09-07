package com.project.ipyang.domain.vaccine.dto;

import com.project.ipyang.common.entity.BaseEntity;
import lombok.Data;

@Data
public class WriteVaccineDto extends BaseEntity {
    private String name;
}
