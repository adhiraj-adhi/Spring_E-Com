package com.ecom.project.services;

import java.util.List;

import com.ecom.project.model.Category;

public interface CategoryService {
	List<Category> getAllCategoriesService();
	boolean createCategoryService(Category category);
//	boolean deleteCategoryService(Long id);
	String deleteCategoryService(Long id);
	Category updateCategoryService(Long categoryId, Category category);
}
