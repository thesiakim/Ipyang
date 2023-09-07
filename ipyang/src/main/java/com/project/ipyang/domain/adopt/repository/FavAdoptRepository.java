package com.project.ipyang.domain.adopt.repository;

import com.project.ipyang.domain.adopt.entity.Adopt;
import com.project.ipyang.domain.adopt.entity.FavAdopt;
import com.project.ipyang.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavAdoptRepository extends JpaRepository<FavAdopt, Long> {
    List<FavAdopt> findByMemberIdOrderByCreatedAtDesc(Long memberId);
    FavAdopt findFavAdoptByMemberAndAdopt(Member member, Adopt adopt);
}
