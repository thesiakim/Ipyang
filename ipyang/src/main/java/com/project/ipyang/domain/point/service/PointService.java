package com.project.ipyang.domain.point.service;

import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.common.response.ResponseDto;
import com.project.ipyang.domain.member.entity.Member;
import com.project.ipyang.domain.member.repository.MemberRepository;
import com.project.ipyang.domain.point.dto.PointChargeDto;
import com.project.ipyang.domain.point.entity.Point;
import com.project.ipyang.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PointService {
    private  final PointRepository pointRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public ResponseDto chargePoint(PointChargeDto request, Long memberId) {

        Optional<Member> member = memberRepository.findById(memberId);

        Point point = Point.builder()
                .pointHistory(request.getPointHistory())
                .type(IpyangEnum.PointType.CHARGE)
                .member(member.get())
                .build();
        pointRepository.save(point);

        Member updateMember  = member.get();
        updateMember.charge(request.getPointHistory());
        memberRepository.save(updateMember);
        return new ResponseDto("포인트충전 완료", HttpStatus.OK.value());
    }
    @Transactional
    public ResponseDto expensePoint(PointChargeDto request, Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);

        if(member.get().getPoint() - request.getPointHistory() < 0){
            return new ResponseDto("포인트가 부족합니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }


        Point point = Point.builder()
                .pointHistory(request.getPointHistory())
                .type(IpyangEnum.PointType.BUY)
                .member(member.get())
                .build();
        pointRepository.save(point);

        Member updateMember  = member.get();
        updateMember.expense(request.getPointHistory());


        memberRepository.save(updateMember);
        return new ResponseDto("포인트차감 완료", HttpStatus.OK.value());


    }

    public ResponseDto incomePoint(PointChargeDto request, Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);

        Point point = Point.builder()
                .pointHistory(request.getPointHistory())
                .type(IpyangEnum.PointType.SELL)
                .member(member.get())
                .build();
        pointRepository.save(point);

        Member updateMember  = member.get();
        updateMember.income(request.getPointHistory());
        memberRepository.save(updateMember);
        return new ResponseDto("포인트가감 완료", HttpStatus.OK.value());
    }
}
