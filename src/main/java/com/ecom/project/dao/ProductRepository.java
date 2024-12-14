package com.ecom.project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.project.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByCategoryCategoryId(Long categoryId);

	List<Product> findByProductNameIgnoreCaseContaining(String keyword);
}
