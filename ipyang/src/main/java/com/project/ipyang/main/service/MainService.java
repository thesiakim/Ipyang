package com.project.ipyang.main.service;

import com.project.ipyang.common.response.ResponseDto;
import com.project.ipyang.domain.adopt.dto.SelectAdoptDto;
import com.project.ipyang.domain.adopt.repository.AdoptRepository;
import com.project.ipyang.domain.board.dto.SelectBoardDto;
import com.project.ipyang.domain.board.repository.BoardRepository;
import com.project.ipyang.domain.product.dto.SelectProductDto;
import com.project.ipyang.domain.product.repository.ProductRepository;
import com.project.ipyang.main.dto.GetTotalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MainService {
    private final BoardRepository boardRepository;
    private final ProductRepository productRepository;
    private final AdoptRepository adoptRepository;

    @Transactional(readOnly = true)
    public ResponseDto getTotal(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;   //요청 받은 페이지

        Pageable modifiedPageable = PageRequest.of(page, pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "id"));

        Page<SelectBoardDto> boards = boardRepository.findAll(modifiedPageable).map(SelectBoardDto::new);
        Page<SelectAdoptDto> adopts = adoptRepository.findAll(modifiedPageable).map(SelectAdoptDto::new);
        Page<SelectProductDto> products = productRepository.findAll(modifiedPageable).map(SelectProductDto::new);

        if(!boards.isEmpty() || !products.isEmpty() || !adopts.isEmpty()) {
            GetTotalDto getTotalDto = new GetTotalDto();

            if(!boards.isEmpty()) getTotalDto.setBoardDtos(boards);
            if(!products.isEmpty()) getTotalDto.setProductDtos(products);
            if(!adopts.isEmpty()) getTotalDto.setAdoptDtos(adopts);

            return new ResponseDto(getTotalDto, HttpStatus.OK.value());
        }
        else return new ResponseDto("데이터가 없습니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

}
