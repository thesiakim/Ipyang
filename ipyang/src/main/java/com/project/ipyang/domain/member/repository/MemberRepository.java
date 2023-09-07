package com.project.ipyang.domain.member.repository;

import com.project.ipyang.domain.member.dto.UpdateMemberDto;
import com.project.ipyang.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByNickname(String nickname);
    Member findByEmail(String email) ;
    boolean existsByEmail(String email);
    Optional<Member> findByNickname(String nickname);



}
