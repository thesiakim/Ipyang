package com.project.ipyang.domain.vaccine.repository;

import com.project.ipyang.domain.vaccine.entity.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccineRepository extends JpaRepository<Vaccine, Long> {
}
