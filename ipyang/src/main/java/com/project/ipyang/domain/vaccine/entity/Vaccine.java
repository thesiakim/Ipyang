package com.project.ipyang.domain.vaccine.entity;

import com.project.ipyang.domain.adopt.entity.Adopt;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Vaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaccine_id")
    private Long id;

    @Column(name = "v_name")
    private String name;

    @OneToMany(mappedBy = "vaccine")
    private List<Adopt> adopts = new ArrayList<>();

    public void update(String inputName) {
        this.name = inputName;
    }

}
