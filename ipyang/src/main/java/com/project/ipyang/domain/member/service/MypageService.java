package com.project.ipyang.domain.member.service;

import com.project.ipyang.common.response.ResponseDto;
import com.project.ipyang.domain.adopt.dto.SelectAdoptDto;
import com.project.ipyang.domain.adopt.dto.SelectFavAdoptDto;
import com.project.ipyang.domain.adopt.entity.Adopt;
import com.project.ipyang.domain.adopt.entity.FavAdopt;
import com.project.ipyang.domain.adopt.repository.AdoptRepository;
import com.project.ipyang.domain.adopt.repository.FavAdoptRepository;
import com.project.ipyang.domain.apply.dto.SelectApplyDto;
import com.project.ipyang.domain.apply.entity.Apply;
import com.project.ipyang.domain.apply.repository.ApplyRepository;
import com.project.ipyang.domain.board.dto.SelectBoardDto;
import com.project.ipyang.domain.board.entity.Board;
import com.project.ipyang.domain.board.repository.BoardRepository;
import com.project.ipyang.domain.inquire.dto.SelectInquireDto;
import com.project.ipyang.domain.inquire.entity.Inquire;
import com.project.ipyang.domain.inquire.repository.InquireRepository;
import com.project.ipyang.domain.member.entity.Member;
import com.project.ipyang.domain.member.repository.MemberRepository;
import com.project.ipyang.domain.product.dto.SelectProductDto;
import com.project.ipyang.domain.product.entity.Product;
import com.project.ipyang.domain.product.repository.ProductRepository;
import com.project.ipyang.main.dto.SelectTotalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final InquireRepository inquireRepository;
    private final ApplyRepository applyRepository;
    private final AdoptRepository adoptRepository;
    private final BoardRepository boardRepository;
    private final ProductRepository productRepository;
    private final FavAdoptRepository favAdoptRepository;

    //회원이 작성한 문의 내역 조회
    @Transactional
    public ResponseDto<List<SelectInquireDto>> selectInquireByMember(Long memberId) {
        List<Inquire> inquires = inquireRepository.findByMemberIdOrderByCreatedAtDesc(memberId);
        if(!inquires.isEmpty()) {
            List<SelectInquireDto> selectInquireDtos = inquires.stream().map(SelectInquireDto::new).collect(Collectors.toList());
            return new ResponseDto(selectInquireDtos, HttpStatus.OK.value());
        } else return new ResponseDto("문의 내역이 없습니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }


    //회원이 제출한 입양 신청서 조회
    @Transactional
    public ResponseDto<List<SelectApplyDto>> selectApplyByMember(Long memberId) {
        List<Apply> applies = applyRepository.findByMemberIdOrderByCreatedAtDesc(memberId);
        if(!applies.isEmpty()) {
            List<SelectApplyDto> selectApplyDtos = applies.stream().map(SelectApplyDto::new).collect(Collectors.toList());
            return new ResponseDto(selectApplyDtos, HttpStatus.OK.value());
        } else return new ResponseDto("제출한 신청서가 없습니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }


    //회원이 작성한 글 조회 (Adopt, Board, Product)
    @Transactional
    public ResponseDto<List<SelectTotalDto>> selectTotalByMember(Long memberId) {
        List<Adopt> adopts = adoptRepository.findByMemberIdOrderByCreatedAtDesc(memberId);
        List<Board> boards = boardRepository.findByMemberIdOrderByCreatedAtDesc(memberId);
        List<Product> products = productRepository.findByMemberIdOrderByCreatedAtDesc(memberId);

        SelectTotalDto selectTotalDto = new SelectTotalDto();

        if(!adopts.isEmpty() || !boards.isEmpty() || !products.isEmpty()) {
            if(!adopts.isEmpty()) {
                List<SelectAdoptDto> adoptDtos = new ArrayList<>();
                for(Adopt adopt : adopts) {
                    SelectAdoptDto selectAdoptDto = adopt.convertDto();
                    adoptDtos.add(selectAdoptDto);
                }
                selectTotalDto.setAdoptDtos(adoptDtos);
            }
            if(!boards.isEmpty()) {
                List<SelectBoardDto> boardDtos = new ArrayList<>();
                for(Board board : boards) {
                    SelectBoardDto selectBoardDto = board.convertSelectDto();
                    boardDtos.add(selectBoardDto);
                }
                selectTotalDto.setBoardDtos(boardDtos);
            }
            if(!products.isEmpty()) {
                List<SelectProductDto> productDtos = new ArrayList<>();
                for(Product product : products) {
                    SelectProductDto selectProductDto = product.convertDto();
                    productDtos.add(selectProductDto);
                }
                selectTotalDto.setProductDtos(productDtos);
            }
            return new ResponseDto(selectTotalDto, HttpStatus.OK.value());
        } else return new ResponseDto("내역이 없습니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }


    //회원의 관심 고양이 조회
    public ResponseDto<List<SelectFavAdoptDto>> selectFavAdoptByMember(Long memberId) {
        List<FavAdopt> favAdopts = favAdoptRepository.findByMemberIdOrderByCreatedAtDesc(memberId);
        List<SelectFavAdoptDto> selectFavAdoptDtos = new ArrayList<>();
        if(!favAdopts.isEmpty()) {
            for(FavAdopt favAdopt : favAdopts) {
                Long favId = favAdopt.getId();
                Long findMemberId = favAdopt.getMember().getId();
                Long adoptId = favAdopt.getAdopt().getId();
                String title = favAdopt.getAdopt().getTitle();

                SelectFavAdoptDto selectFavAdoptDto = favAdopt.convertSelectDto(favId, findMemberId, adoptId, title);
                selectFavAdoptDtos.add(selectFavAdoptDto);
            }
            return new ResponseDto(selectFavAdoptDtos, HttpStatus.OK.value());
        } else return new ResponseDto("내역이 없습니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
