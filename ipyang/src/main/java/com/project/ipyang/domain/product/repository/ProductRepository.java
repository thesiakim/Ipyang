package com.project.ipyang.domain.product.repository;

import com.project.ipyang.domain.product.dto.ProductDto;
import com.project.ipyang.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByMemberIdOrderByCreatedAtDesc(Long memberId);

}
