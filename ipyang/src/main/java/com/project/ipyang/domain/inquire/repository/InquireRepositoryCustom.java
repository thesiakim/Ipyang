package com.project.ipyang.domain.inquire.repository;

import com.project.ipyang.domain.inquire.dto.InquireListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InquireRepositoryCustom {
    Page<InquireListDto> searchInquire(String searchKeyword, String searchType, Pageable pageable, int page);
}
