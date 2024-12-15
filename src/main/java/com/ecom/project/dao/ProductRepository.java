package com.ecom.project.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.project.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Page<Product> findByCategoryCategoryId(Long categoryId, Pageable pageable);

	Page<Product> findByProductNameIgnoreCaseContaining(String keyword, Pageable pageable);
}
