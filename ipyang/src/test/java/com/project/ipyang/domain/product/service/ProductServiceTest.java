//package com.project.ipyang.domain.product.service;
//
//import com.project.ipyang.domain.member.entity.Member;
//import com.project.ipyang.domain.member.repository.MemberRepository;
//import com.project.ipyang.domain.product.dto.ProductDto;
//import com.project.ipyang.domain.product.repository.ProductRepository;
//import org.aspectj.lang.annotation.Before;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//class ProductServiceTest {
//
//    @Mock
//    private MemberRepository memberRepository;
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @InjectMocks
//    private ProductService productService;
//
//
//    @Test
//    public void testSelectProduct_Success() {
//        // ProductService의 selectProduct() 메서드 호출
//        ProductDto productDto = new ProductDto();
//        productDto.setMember_id(1L); // 유효한 멤버 ID로 설정
//        // 필요한 경우 다른 필드 값도 설정
//        ProductDto result = productService.selectProduct(productDto);
//
//        // 테스트 결과 검증
//        Assertions.assertNotNull(result);
//        // 추가적인 검증 로직 추가
//    }
//
//    // 추가적인 테스트 케이스 작성 가능
//
//}