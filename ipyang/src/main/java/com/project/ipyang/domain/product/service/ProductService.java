package com.project.ipyang.domain.product.service;

import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.common.response.ResponseDto;
import com.project.ipyang.common.util.S3Utils;
import com.project.ipyang.config.SessionUser;
import com.project.ipyang.domain.board.entity.Board;
import com.project.ipyang.domain.member.entity.Member;
import com.project.ipyang.domain.member.repository.MemberRepository;
import com.project.ipyang.domain.product.dto.*;
import com.project.ipyang.domain.product.entity.Product;
import com.project.ipyang.domain.product.entity.ProductImg;
import com.project.ipyang.domain.product.repository.ProductRepository;
import com.project.ipyang.domain.product.repository.ProductImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final ProductImgRepository productImgRepository;
    private final HttpSession session;
    private final S3Utils s3Utils;

    @Transactional
    public ResponseDto createProduct(IpyangEnum.ProductType pT,InsertProductDto request) {
        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");
        Long memberId = loggedInUser.getId();


        Member member = memberRepository.findById(memberId).orElse(null);
        Product createProduct = null;

        if(request.getImageFiles().isEmpty()){
            Product product = Product.builder()
                    .name(request.getName())
                    .content(request.getContent())
                    .status(IpyangEnum.Status.N)
                    .price(request.getPrice())
                    .type(pT)
                    .loc(request.getLoc())
                    .member(member)
                    .build();

            productRepository.save(product);
        }
        else {
            Product product = Product.builder()
                    .name(request.getName())
                    .content(request.getContent())
                    .status(IpyangEnum.Status.N)
                    .price(request.getPrice())
                    .type(pT)
                    .loc(request.getLoc())
                    .member(member)
                    .build();

            createProduct =   productRepository.save(product);
            Long savedId = createProduct.getId();
            Product productId = productRepository.findById(savedId).get();
            for (MultipartFile productFile : request.getImageFiles() ) {
                String imgOriginFile = productFile.getOriginalFilename();
                String imgUrl = s3Utils.uploadFileToS3(productFile,"product");

                ProductImg productImg = new ProductImg(imgOriginFile,imgUrl,productId);
                productImgRepository.save(productImg);

            }


        }


        return new ResponseDto("판매글 작성성공", HttpStatus.OK.value());
    }


    //전체 상품 데이터 가져오기
    @Transactional
    public ResponseDto selectAllProduct( Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;
        int blockLimit = 5;
        Page<Product> products = productRepository.findAll( PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        Page<SelectProductDto> selectProductDtos = products.map(product -> new SelectProductDto(
            product.getId(),
            product.getName(),
            product.getStatus(),
            product.getPrice(),
            product.getType(),
            product.getLoc(),
            product.getMember().getId(),
            product.getMember().getNickname(),
            product.getCreatedAt()
        ));

                //products.stream().map(SelectProductDto::new).collect(Collectors.toList());


        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < selectProductDtos.getTotalPages()) ? startPage + blockLimit - 1 : selectProductDtos.getTotalPages();

        ProductPageDto productPage = new ProductPageDto(selectProductDtos,startPage,endPage);

        if(!selectProductDtos.isEmpty()) {
            return new ResponseDto(productPage, HttpStatus.OK.value());
        } else return new ResponseDto("가져올 데이터가 없습니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Transactional
    public ResponseDto<ReadProductDto> readSomeProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent()){
            return new ResponseDto("존재하지 않는 글입니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        Product findProduct = productOptional.get();
        ReadProductDto readProductDto = findProduct.convertReadDto();
        List<String> imgList = new ArrayList<>();
        for (ProductImg productImg : findProduct.getProductImgs()) {
            imgList.add(productImg.getImgStoredFile());
        }
        if(!imgList.isEmpty()) readProductDto.setImgList(imgList);

        return new ResponseDto(readProductDto,HttpStatus.OK.value());
    }


    @Transactional
    public Object updateProduct(Long id,UpdateProductDto request) {
        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");
        Long memberId = loggedInUser.getId();

        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent()) {
            return new ResponseDto("존재하지 않는 판매글입니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        Product findProduct = productOptional.get();

        if (memberId.equals(findProduct.getMember().getId())){
            findProduct.UpdateProduct(request.getName(),request.getLoc(),request.getPrice(),request.getContent());
             productRepository.save(findProduct);
            return new ResponseDto("게시물 수정 성공.", HttpStatus.OK.value());
        }
        else {
            return new ResponseDto("판매글 작성자만 수정가능합니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }


    }



    @Transactional
    public ResponseDto soldoutProduct( long id) {

        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");
        Long memberId = loggedInUser.getId();
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent()) {
            return new ResponseDto("존재하지 않는 판매글입니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        Product findProduct = productOptional.get();

        if (memberId.equals(findProduct.getMember().getId())){
            findProduct.soldout();
            productRepository.save(findProduct);
            return new ResponseDto("판매가 완료되었습니다.", HttpStatus.OK.value());
        }
        else {
            return new ResponseDto("판매글 작성자만 수정가능합니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }




    @Transactional
    public ResponseDto deleteProduct( long id) {
        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");
        Long memberId = loggedInUser.getId();

        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            if (memberId.equals(product.getMember().getId())) {
                productRepository.delete(product);
                return new ResponseDto("판매글삭제 성공", HttpStatus.OK.value());
            }
            else {
                return new ResponseDto("판매글 작성자만 삭제가능합니다.", HttpStatus.INTERNAL_SERVER_ERROR.value());
            }

        }
        else {
            return new ResponseDto("판매글삭제 실패", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }


}




