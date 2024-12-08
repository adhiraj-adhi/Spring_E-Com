package com.ecom.project.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.ecom.project.dao.CategoryRepository;
import com.ecom.project.exceptions.APIException;
import com.ecom.project.exceptions.ResourceNotFoundException;
import com.ecom.project.model.Category;

@Service
public class CategoryServiceImpl implements CategoryService {
	private CategoryRepository catRepository;

	public CategoryServiceImpl(CategoryRepository catRepository) {
		this.catRepository = catRepository;
	}

	@Override
	public List<Category> getAllCategoriesService() {
		List<Category> categories = catRepository.findAll();
		if(categories.isEmpty())
			throw new APIException("There's no category present");
		return categories;
	}

	@Override
	public boolean createCategoryService(Category category) {
		boolean status = false;

		// Before saving we need to check whether the category with same categoryName exists
		Category savedCategory = catRepository.findByCategoryNameIgnoreCase(category.getCategoryName());
		if (savedCategory == null) {
			try {
				catRepository.save(category);
				status = true;
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			throw new APIException("Category with the name " + category.getCategoryName() + " already exists");
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

		if (optCategory.isEmpty())
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found");
			throw new ResourceNotFoundException("Category", id, "categoryId");
		else {
			catRepository.deleteById(id);
		}
		return "Resource deleted successfully";
	}

	@Override
	public Category updateCategoryService(Long categoryId, Category category) {
		// Before updating we need to check whether the category with updated categoryName exists
		Category savedCategory = catRepository.findByCategoryNameIgnoreCase(category.getCategoryName());
		if (savedCategory != null) {
			throw new APIException("Category with the name " + category.getCategoryName() + " already exists");
		} else {
			Optional<Category> optionalCategory = catRepository.findById(categoryId);
			if (optionalCategory.isEmpty())
//				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found");
				throw new ResourceNotFoundException("Category", categoryId, "categoryId");

			Category oldCategory = optionalCategory.get();
			oldCategory.setCategoryName(category.getCategoryName());
			return catRepository.save(oldCategory);
		}
	}

}
