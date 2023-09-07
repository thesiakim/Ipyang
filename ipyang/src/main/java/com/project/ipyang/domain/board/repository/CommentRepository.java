package com.project.ipyang.domain.board.repository;

import com.project.ipyang.domain.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByBoardId(Long board);

}
