package com.project.ipyang.domain.member.service;


import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.common.entity.BaseEntity;
import com.project.ipyang.common.response.ResponseDto;
import com.project.ipyang.config.SessionUser;
import com.project.ipyang.domain.member.dto.*;
import com.project.ipyang.domain.member.entity.Member;
import com.project.ipyang.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private  final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpSession session; // HttpSession 객체를 주입받습니다.


    public MemberDto createMember(InsertMemberDto memberDto) {

        Member member = Member.builder().name(memberDto.getName())
                .email(memberDto.getEmail())
                .nickname(memberDto.getNickname())
                .passwd(memberDto.getPasswd())
                .name(memberDto.getName())
                .phone(memberDto.getPhone())
                .memberRole(IpyangEnum.MemberRoleType.USER)
                .delYn(IpyangEnum.Status.N)
                .address(memberDto.getAddress())
                .build();
        memberRepository.save(member);
        return new MemberDto();
    }

    //전체 멤버 데이터 가져오기
    public ResponseDto selectAllMember() {
        List<Member> members = memberRepository.findAll();
        List<SelectMemberDto> selectMemberDtos = members.stream().map(SelectMemberDto::new).collect(Collectors.toList());

        if(!selectMemberDtos.isEmpty()) {
            return new ResponseDto(selectMemberDtos, HttpStatus.OK.value());
        } else return new ResponseDto("가져올 데이터가 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());

    }

    //회원가입 시 유효성 검사
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for(FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return  validatorResult;
    }


    //닉네임 중복 확인
    public ResponseDto checkDuplicateNickname(String nickname) {
        boolean isDuplicate = memberRepository.existsByNickname(nickname);
        if (isDuplicate) {
            return new ResponseDto("중복된 닉네임입니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        } else {
            return new ResponseDto("중복되지 않은 닉네임입니다.", HttpStatus.OK.value());
        }
    }


    //회원가입 정보 저장
    public ResponseDto memberInfoSave(SignUpMemberDto signUpMemberDto, PasswordEncoder passwordEncoder) {
        Member member = signUpMemberDto.toEntity(passwordEncoder);
        Member savedMember = memberRepository.save(member);
        if(savedMember != null) {
            return new ResponseDto("회원가입이 성공했습니다.", HttpStatus.OK.value());
        } else return new ResponseDto("회원가입을 실패했습니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }


    public ResponseDto checkDuplicateEmail(String email) {

        boolean isDuplicate = memberRepository.existsByEmail(email);
        if (isDuplicate) {
            return new ResponseDto("중복된 이메일입니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        } else {
            return new ResponseDto("중복되지 않은 이메일입니다.", HttpStatus.OK.value());
            }

    }

    public ResponseDto loginMember(String email, String passwd) {
        Optional<Member> member = Optional.ofNullable(memberRepository.findByEmail(email));

        if (member.isPresent()) {
            MemberDto memberDto = member.get().convertDto();
            if (memberDto.getPasswd().equals(passwd)) {
                // 패스워드가 일치하면 로그인 성공으로 처리합니다.
                SessionUser sessionUser = new SessionUser(memberDto);
                session.setAttribute("loggedInUser", sessionUser);

                return new ResponseDto("로그인에 성공하였습니다.", HttpStatus.OK.value());
            } else {
                // 패스워드가 일치하지 않으면 로그인 실패로 처리합니다.
                return new ResponseDto("비밀번호가 일치하지 않습니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        }
        else {

            return new ResponseDto("일치하는 회원이 없습니다" , HttpStatus.INTERNAL_SERVER_ERROR.value());

        }
    }




    public ResponseDto updateMember(UpdateMemberDto request,Long memberId) {
        // 회원 정보를 데이터베이스에서 조회합니다.
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        if (!memberOptional.isPresent()) {
            // 해당 회원이 존재하지 않는 경우 에러 응답을 반환합니다.
            return new ResponseDto("존재하지 않는 회원입니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        // 기존 회원 정보를 가져옵니다.
        Member member = memberOptional.get();
        MemberDto updateMember = member.convertDto();
        updateMember.setNickname(request.getNickname());
        updateMember.setPasswd(request.getPasswd());
        updateMember.setPhone(request.getPhone());
        updateMember.setAddress(request.getAddress());

        memberRepository.save(updateMember.toEntity());

        // 성공 응답을 반환합니다.
        return new ResponseDto("회원 정보가 업데이트되었습니다.", HttpStatus.OK.value());
    }


    @Transactional
    public ResponseDto withdrawMember(Long id  ) {
        Optional<Member> memberOptional = memberRepository.findById(id);
        if (!memberOptional.isPresent()) {
            return new ResponseDto("존재하지 않는 회원입니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        Member member = memberOptional.get();
        member.withdraw(); //N-> Y
        member.delete();    // delete at  날짜추가
        if (memberOptional.get().getDelYn() == IpyangEnum.Status.Y){

            return new ResponseDto("30일뒤 회원이 탈퇴됩니다.", HttpStatus.OK.value());
        }
        else {
            return new ResponseDto("에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

    }



    public ResponseDto<MemberDto> deleteMember( Long id) {
        Optional<Member> memberOptional = memberRepository.findById(id);


        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            memberRepository.delete(member);
            return new ResponseDto("회원탈퇴 성공", HttpStatus.OK.value());
        }
        else {
            return new ResponseDto("회원탈퇴 실패", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }


}
