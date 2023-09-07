//package com.project.ipyang.init;
//
//import com.project.ipyang.domain.member.dto.MemberDto;
//import com.project.ipyang.domain.member.repository.MemberRepository;
//import lombok.NoArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.PostConstruct;
//import java.util.Random;
//
//
//
//@NoArgsConstructor
//public class InitData {
//    private final InitService initService;
//
//    @PostConstruct
//    public void init(){
//        initService.dbInit();
//    }
//
//
//    @NoArgsConstructor
//    @Transactional
//    static class InitService{
//        private final MemberRepository memberRepository;
//
//
//
//        public void dbInit(){
//            MemberDto member;
//            Random random = new Random();
//            for (int i = 0; i < 50; i++) {
//                member = new MemberDto();
//
//                //멤버 데이터 세팅
//                member.setEmail("test"+i+"@test.com");
//                member.setNickname("test"+i);
//                member.setPasswd("test");
//                member.setPhone("010"+(random.nextInt(9999)+1000)+(random.nextInt(9999)+1000));
//                member.setCommon_role("user");
//                member.setAddress("test");
//                member.setPoint(String.valueOf((random.nextInt(9999)+1)));
//                memberRepository.save(member);
//            }
//
//        }
//    }
//}
