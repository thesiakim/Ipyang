package com.project.ipyang.domain.catType.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CatType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cat_id")
    private Long id;

    @Column(name = "c_type")
    private String type;

    public void update(String inputType) {
        this.type = inputType;
    }

    //품종 수정
    public void updatetype(String inputType) {
        this.type = inputType;
    }

}
