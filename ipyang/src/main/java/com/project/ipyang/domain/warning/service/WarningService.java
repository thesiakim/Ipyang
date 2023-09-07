package com.project.ipyang.domain.warning.service;

import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.common.response.ResponseDto;
import com.project.ipyang.domain.board.entity.Board;
import com.project.ipyang.domain.board.repository.BoardRepository;
import com.project.ipyang.domain.member.entity.Member;
import com.project.ipyang.domain.member.repository.MemberRepository;
import com.project.ipyang.domain.warning.dto.AddWordsDto;
import com.project.ipyang.domain.warning.dto.InsertWarningDto;
import com.project.ipyang.domain.warning.entity.BadWords;
import com.project.ipyang.domain.warning.entity.Warning;
import com.project.ipyang.domain.warning.repository.BadWordsRepository;
import com.project.ipyang.domain.warning.repository.WarningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarningService {
    private final WarningRepository warningRepository;
    private final MemberRepository memberRepository;
    private final BadWordsRepository badWordsRepository;
    private final BoardRepository boardRepository;
    @Transactional
    public ResponseDto addBadWords(AddWordsDto addWordsDto,Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);

        BadWords bw = BadWords.builder()
                .words(addWordsDto.getWords())
                .member(member)
                .build();

       badWordsRepository.save(bw).getId();

        return  new ResponseDto("비속어가 추가되었습니다.", HttpStatus.OK.value());

    }
    @Transactional
    public ResponseDto createWarning(IpyangEnum.WarningReason warningReason,
            InsertWarningDto insertWarningDto, Long memberId) {
        //세션으로 신고자id가져옴
        Member member = memberRepository.findById(memberId).orElse(null);
        //suspect에 해당하는 board_id게시판 글 가져옴
        Optional<Board> boardOptional = boardRepository.findById(insertWarningDto.getSuspect());

        Long writer;
        if (boardOptional.isPresent()) {

            Board board = boardOptional.get();
            //작성자아이디 저장
            writer = board.getMember().getId();

            //욕설관련 신고 사유일경우
            if (warningReason == IpyangEnum.WarningReason.BAD_WORDS) {

                String warn = board.getContent();

                String trimText = warn.trim();

                trimText = trimText.replaceAll(" ", "");

                List<BadWords> wordsList = badWordsRepository.findAll();

                int cnt = 0;

                for (BadWords badWords : wordsList) {

                    boolean result = trimText.contains(badWords.getWords());
                    if (result) cnt++;
                    if (cnt >= 1) {
                        break;
                    }
                }
                if (cnt >= 1) {

                    //신고당한 사용자 제제
                } else {
                    //신고한 사용자 제제
                    return new ResponseDto("허위 신고입니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());
                }

            }


        } else {
            return new ResponseDto("존재하지 않는 게시글입니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }


        Warning warning = Warning.builder()
                .suspect(writer)
                .reason(warningReason)
                .member(member)
                .build();
        warningRepository.save(warning);
        return new ResponseDto("신고가 접수되었습니다.", HttpStatus.OK.value());
    }
}
