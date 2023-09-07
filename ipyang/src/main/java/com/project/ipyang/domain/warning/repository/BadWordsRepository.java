package com.project.ipyang.domain.warning.repository;

import com.project.ipyang.domain.warning.entity.BadWords;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadWordsRepository extends JpaRepository<BadWords,Long> {
}
