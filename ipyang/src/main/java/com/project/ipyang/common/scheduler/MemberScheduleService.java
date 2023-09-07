package com.project.ipyang.common.scheduler;

import com.project.ipyang.domain.member.entity.Member;
import com.project.ipyang.domain.member.repository.MemberRepository;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.project.ipyang.domain.member.entity.QMember.member;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberScheduleService {

    private final JPAQueryFactory queryFactory;
    private final MemberRepository memberRepo;
// 초 분 시 일 월 요일
//    @Scheduled(cron = "0/10 * * * * *") // 10초마다 돌게함
    @Scheduled(cron = "0 0 0 * * *")
    public void memberDelete() {
        List<Member> deleteMemberList = queryFactory.selectFrom(member)
                .where(Expressions.dateTemplate(LocalDateTime.class,"TIMESTAMPADD(MONTH,1,{0})",member.deletedAt).loe(LocalDateTime.now()))
                .fetch();

        if(!deleteMemberList.isEmpty()){
            log.info("데이터 삭제");
            deleteMemberList.forEach(member -> memberRepo.delete(member));
        }else{
            log.info("데이터 없음");
        }
    }
}
