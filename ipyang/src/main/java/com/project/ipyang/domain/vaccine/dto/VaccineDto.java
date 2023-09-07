package com.project.ipyang.domain.vaccine.dto;

import com.project.ipyang.domain.vaccine.entity.Vaccine;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@RequiredArgsConstructor
public class VaccineDto {
    private Long id;
    private String name;

    public VaccineDto(Vaccine vaccine) {
        this.id = vaccine.getId();
        this.name = vaccine.getName();
    }
}
