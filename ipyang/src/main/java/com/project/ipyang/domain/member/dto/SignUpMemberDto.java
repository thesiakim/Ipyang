package com.project.ipyang.domain.member.dto;

import com.project.ipyang.domain.member.entity.Member;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpMemberDto {

    //필수 입력값
    @NotBlank(message = "이메일을 입력해주십시오")
    @Email(message = "유효하지 않은 이메일 형식입니다")
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$")
    private String email;

    @NotBlank
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "특수문자를 제외하고 2자리 이상, 10자리 이하로 입력해주십시오.")
    private String nickname;

    @NotBlank(message = "비밀번호를 입력해주십시오")
    @Length(min = 6, max = 15, message = "6자리 이상, 15자리 이하로 입력해주십시오")
    private String passwd;

    @NotBlank(message = "이름을 입력해주십시오")
    private String name;

    @NotBlank(message = "전화번호를 입력해주십시오")
    @Pattern(regexp = "\\d{2,3}[- ]?\\d{3,4}[- ]?\\d{4}")
    private String phone;

    //선택 입력값
    private String address;
    private String imgContext;
    private String imgOriginFile;
    private String imgStoredFile;

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder().email(this.email)
                               .nickname(this.nickname)
                               .passwd(passwordEncoder.encode(this.passwd))
                               .name(this.name)
                               .phone(this.phone)
                               .address(this.address)
                               .imgContext(this.imgContext)
                               .imgOriginFile(this.imgOriginFile)
                               .imgStoredFile(this.imgStoredFile)
                               .build();
    }




}
