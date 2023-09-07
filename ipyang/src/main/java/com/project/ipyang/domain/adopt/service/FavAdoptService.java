package com.project.ipyang.domain.adopt.service;

import com.project.ipyang.common.response.ResponseDto;
import com.project.ipyang.domain.adopt.entity.Adopt;
import com.project.ipyang.domain.adopt.entity.FavAdopt;
import com.project.ipyang.domain.adopt.repository.AdoptRepository;
import com.project.ipyang.domain.adopt.repository.FavAdoptRepository;
import com.project.ipyang.domain.member.entity.Member;
import com.project.ipyang.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavAdoptService {

    private final MemberRepository memberRepository;
    private final AdoptRepository adoptRepository;
    private final FavAdoptRepository favAdoptRepository;

    /*
     * 2023-07-05
     * 이후 개발 시 API를 분리할 수도 있기 때문에 보관
     * */

/*    //관심 고양이 등록
    @Transactional
    public ResponseDto registerFav(Long id, Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        if(!member.isPresent()) return new ResponseDto("로그인이 필요합니다", HttpStatus.INTERNAL_SERVER_ERROR.value()); //이거 지워야할듯?

        Optional<Adopt> adopt = adoptRepository.findById(id);
        if(!adopt.isPresent()) return new ResponseDto("존재하지 않는 글입니다", HttpStatus.INTERNAL_SERVER_ERROR.value());

        //등록 여부 조회
        if(!favAdoptRepository.existsByMemberAndAdopt(member.get(), adopt.get())) {
            FavAdopt favAdopt = FavAdopt.builder()
                    .adopt(adopt.get())
                    .member(member.get())
                    .build();

            Long savedId = favAdoptRepository.save(favAdopt).getId();

            if(savedId != null) return new ResponseDto("관심글로 등록되었습니다", HttpStatus.OK.value());
            else return new ResponseDto("에러가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR.value());

        } else return new ResponseDto("이미 등록된 글입니다", HttpStatus.INTERNAL_SERVER_ERROR.value());

    }


    //관심 고양이 삭제
    @Transactional
    public ResponseDto removeRegisterFav(Long id, Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        Optional<Adopt> adopt = adoptRepository.findById(id);
        if(!adopt.isPresent()) return new ResponseDto("존재하지 않는 글입니다", HttpStatus.INTERNAL_SERVER_ERROR.value());

    }*/

    //------------------------------------------------------------------------------------------------------------------

    //관심 고양이 등록 및 삭제
    @Transactional
    public ResponseDto favorite(Long id, Long memberId) {
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        Optional<Adopt> adoptOptional = adoptRepository.findById(id);
        if(!adoptOptional.isPresent()) return new ResponseDto("존재하지 않는 글입니다", HttpStatus.INTERNAL_SERVER_ERROR.value());

        Member member = memberOptional.get();
        Adopt adopt = adoptOptional.get();
        FavAdopt favAdopt = favAdoptRepository.findFavAdoptByMemberAndAdopt(member, adopt);

        //관심글로 등록된 경우
        if(favAdopt != null) {
            favAdopt.cancelFavAdopt();
            favAdoptRepository.delete(favAdopt);
            return new ResponseDto("관심글 등록을 취소했습니다", HttpStatus.OK.value());

        //관심글로 미등록된 경우
        } else {
            favAdopt = new FavAdopt(member, adopt);
            favAdoptRepository.save(favAdopt);
            return new ResponseDto("관심글로 등록했습니다", HttpStatus.OK.value());
        }
    }
}
