package com.project.ipyang.domain.inquire.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class WriteInquireDto {
    @NotEmpty(message = "제목을 입력해주십시오")
    private String title;

    @NotEmpty(message = "내용을 입력해주십시오")
    private String content;

    @NotEmpty(message = "비밀번호를 입력해주십시오")
    @Size(min = 4, max = 4, message = "비밀번호는 4자리로 입력해주십시오")
    private String passwd;

    private IpyangEnum.Status status;
    private List<MultipartFile> inquireFile;
}
