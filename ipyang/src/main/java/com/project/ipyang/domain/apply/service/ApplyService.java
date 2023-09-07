package com.project.ipyang.domain.apply.service;

import com.project.ipyang.common.response.ResponseDto;
import com.project.ipyang.domain.adopt.entity.Adopt;
import com.project.ipyang.domain.adopt.repository.AdoptRepository;
import com.project.ipyang.domain.apply.dto.WriteApplyDto;
import com.project.ipyang.domain.apply.entity.Apply;
import com.project.ipyang.domain.apply.repository.ApplyRepository;
import com.project.ipyang.domain.member.entity.Member;
import com.project.ipyang.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final MemberRepository memberRepository;
    private final AdoptRepository adoptRepository;
    private final ApplyRepository applyRepository;

    //입양 신청서 제출
    @Transactional
    public ResponseDto submitApply(Long id, WriteApplyDto request, Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        if(!member.isPresent()) return new ResponseDto("로그인이 필요합니다", HttpStatus.INTERNAL_SERVER_ERROR.value());

        Optional<Adopt> adopt = adoptRepository.findById(id);
        if(!adopt.isPresent()) return new ResponseDto("존재하지 않는 글입니다", HttpStatus.INTERNAL_SERVER_ERROR.value());

        Apply apply = request.toEntity(member.get(), adopt.get());
        Long savedId = applyRepository.save(apply).getId();

        if(savedId != null) return new ResponseDto("신청서가 제출되었습니다.", HttpStatus.OK.value());
        else return new ResponseDto("에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }




}
