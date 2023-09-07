package com.project.ipyang.domain.member.dto;

import com.project.ipyang.common.entity.BaseEntity;
import lombok.*;

import javax.validation.constraints.*;


@Setter
@Getter
@ToString(exclude = "passwd")
public class InsertMemberDto  {
    @NotBlank(message = "이메일은 필수 값 입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "닉네임은 필수 값 입니다.")
    @Size(min = 1,max = 10)
    private String nickname;

    private String passwd;

    @NotBlank
    private String name;

    @NotBlank
    @Max(value = 11,message = "11자리 이하만 가능합니다")
    private String phone;


    private String address;

//    private String img_original;
//    private String img_stored;

}
