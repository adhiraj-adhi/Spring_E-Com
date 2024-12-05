package com.ecom.project.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecom.project.model.Category;

@Service
public class CategoryServiceImpl implements CategoryService {
	private List<Category> categories = new ArrayList<Category>();

	@Override
	public List<Category> getAllCategoriesService() {
		return categories;
	}

	@Override
	public boolean createCategoryService(Category category) {
		boolean status = false;
		try {
			categories.add(category);
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

//	@Override
//	public boolean deleteCategoryService(Long id) {
//		boolean status = false;
//		try {
//			status = categories.removeIf(category -> category.getCategoryId().equals(id));
//		} 
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		return status;
//	}

	@Override
	public String deleteCategoryService(Long id) {
		boolean status = false;
		status = categories.removeIf(category -> category.getCategoryId().equals(id));
		if (!status)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found");

		return "Resource deleted successfully";
	}

	@Override
	public Category updateCategoryService(Long categoryId, Category category) {
		Optional<Category> optionalCategory = categories.stream().filter(cat -> cat.getCategoryId().equals(categoryId)).findFirst();
		if (optionalCategory.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found");
		
		Category oldCategory = optionalCategory.get();
		oldCategory.setCategoryName(category.getCategoryName());
		return oldCategory;
	}

}
