package com.project.ipyang.domain.adopt.dto;

import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.domain.adopt.entity.Adopt;
import com.project.ipyang.domain.adopt.entity.AdoptImg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class WriteAdoptDto {
    @NotEmpty(message = "제목을 입력해주십시오")
    private String title;

    @NotEmpty(message = "내용을 입력해주십시오")
    private String content;

    private Long vacId;
    private Long catId;
    private int view;
    private String name;
    private String gender;
    private String weight;
    private String age;
    private String neu;
    private IpyangEnum.Status status;

    private List<MultipartFile> adoptFile;
    private List<String> imgOriginFile;   //원본 파일 이름
    private List<String> imgStoredFile;   //서버에 저장된 파일 이름
}
