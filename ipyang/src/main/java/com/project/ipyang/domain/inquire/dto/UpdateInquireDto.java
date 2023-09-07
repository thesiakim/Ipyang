package com.project.ipyang.domain.inquire.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UpdateInquireDto {
    private String title;
    private String content;
    private String passwd;
    private List<MultipartFile> inquireFile;

}
