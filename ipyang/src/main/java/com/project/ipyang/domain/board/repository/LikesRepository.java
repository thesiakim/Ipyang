package com.project.ipyang.domain.board.repository;

import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.domain.board.entity.Likes;
import com.project.ipyang.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes,Long> {
    Optional<Likes> findByTargetIdAndMember(Long id, Member memberId);

    Long countByTargetTypeAndTargetId(IpyangEnum.LikeType targetType,Long id);

    List<Likes> findByTargetId(Long id);

    Optional<Likes> findByTargetTypeAndTargetIdAndMember(IpyangEnum.LikeType likeType, Long id, Member member);

}
