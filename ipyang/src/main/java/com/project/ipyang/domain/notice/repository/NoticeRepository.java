package com.project.ipyang.domain.notice.repository;

import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.domain.notice.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice,Long> {
    Page<Notice> findByCategory(IpyangEnum.NoticeCategory category, Pageable pageable);
}
