package com.project.ipyang.domain.product.controller;

import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.common.response.ResponseDto;
import com.project.ipyang.config.SessionUser;
import com.project.ipyang.domain.board.dto.BoardDto;
import com.project.ipyang.domain.member.entity.Member;
import com.project.ipyang.domain.product.dto.*;
import com.project.ipyang.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

        private final ProductService productService;
        private final HttpSession session;

        @PostMapping(value = "/v1/product/{type}/write",
                consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseDto createProduct(@PathVariable("type")IpyangEnum.ProductType pT,
                                         @ModelAttribute InsertProductDto request)
                throws IOException {
                return productService.createProduct(pT,request);
        }

        //전체 상품 데이터 가져오기
        @GetMapping(value = "/v1/product")
        public ResponseDto<List<SelectProductDto>> selectAllProduct(@PageableDefault(page = 1) Pageable pageable
        )  {
            return productService.selectAllProduct(pageable);
        }


        //상품 자세히보기
        @GetMapping(value = "v1/product/{id}")
        public ResponseDto<ReadProductDto> readProduct(@PathVariable("id")Long id){
                return new ResponseDto(productService.readSomeProduct(id));
        }



        //판매글 수정하기
        @PutMapping(value = "/v1/product/{id}")
        public ResponseDto<ProductDto> updateProduct(@PathVariable("id")Long id,@RequestBody UpdateProductDto request) {


                return new ResponseDto(productService.updateProduct(id,request));
        }

        //판매완료  status N->Y
        @PutMapping(value = "/v1/product/{id}/soldout")
        public ResponseDto<ProductDto> soldProduct(@PathVariable("id")Long id ) {



                return productService.soldoutProduct(id);
        }


        //판매글 삭제하기
        @DeleteMapping(value = "/v1/product/{id}")
        public ResponseDto<ProductDto> deleteProduct(@PathVariable("id")Long id ) {
                return  productService.deleteProduct(id);
        }


}
