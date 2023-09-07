package com.project.ipyang.domain.adopt.repository;

import com.project.ipyang.domain.adopt.dto.AdoptDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface AdoptRepositoryCustom {

    Page<AdoptDto> findFilteredAdopt(String adopted, List<Long> catIds, List<Long> vacIds, int page,
                                     String searchKeyword, String searchType,
                                     Pageable pageable);
}
