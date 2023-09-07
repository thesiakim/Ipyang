package com.project.ipyang.domain.apply.repository;

import com.project.ipyang.domain.apply.entity.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
    List<Apply> findByMemberIdOrderByCreatedAtDesc(Long memberId);
}
