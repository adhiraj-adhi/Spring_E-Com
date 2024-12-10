package com.ecom.project.services;

import com.ecom.project.payload.CategoryDTO;
import com.ecom.project.payload.CategoryResponse;

public interface CategoryService {
	CategoryResponse getAllCategoriesService(Integer pageNumber, Integer pageSize);
	CategoryDTO createCategoryService(CategoryDTO categoryDTO);
	CategoryDTO deleteCategoryService(Long id);
	CategoryDTO updateCategoryService(Long categoryId, CategoryDTO categoryDTO);
}
