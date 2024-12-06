package com.ecom.project.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecom.project.dao.CategoryRepository;
import com.ecom.project.model.Category;

@Service
public class CategoryServiceImpl implements CategoryService {
	private CategoryRepository catRepository;
	
	public CategoryServiceImpl(CategoryRepository catRepository) {
		this.catRepository = catRepository;
	}

	@Override
	public List<Category> getAllCategoriesService() {
		return catRepository.findAll();
	}

	@Override
	public boolean createCategoryService(Category category) {
		boolean status = false;
		try {
			catRepository.save(category);
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
		Optional<Category> optCategory = catRepository.findById(id);
		
		if(optCategory.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found");
		else {
			catRepository.deleteById(id);
		}
		return "Resource deleted successfully";
	}

	@Override
	public Category updateCategoryService(Long categoryId, Category category) {
		Optional<Category> optionalCategory = catRepository.findById(categoryId);
		if (optionalCategory.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found");
		
		Category oldCategory = optionalCategory.get();
		oldCategory.setCategoryName(category.getCategoryName());
		return catRepository.save(oldCategory);
	}

}
