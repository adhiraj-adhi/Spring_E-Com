package com.ecom.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.project.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
