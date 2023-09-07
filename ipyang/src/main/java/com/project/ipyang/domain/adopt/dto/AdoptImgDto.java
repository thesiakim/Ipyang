package com.project.ipyang.domain.adopt.dto;

import com.project.ipyang.common.entity.BaseEntity;
import com.project.ipyang.domain.adopt.entity.AdoptImg;
import lombok.*;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class AdoptImgDto extends BaseEntity {
    private Long id;
    private String imgOriginFile;
    private String imgStoredFile;

    public AdoptImg toEntity() {
        return AdoptImg.builder()
                .id(id)
                .imgOriginFile(imgOriginFile)
                .imgStoredFile(imgStoredFile)
                .build();
    }

}
