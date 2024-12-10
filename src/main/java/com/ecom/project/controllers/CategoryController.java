package com.ecom.project.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.project.config.AppConstants;
import com.ecom.project.payload.CategoryDTO;
import com.ecom.project.payload.CategoryResponse;
import com.ecom.project.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CategoryController {
	
	private CategoryService catService;
	
	public CategoryController(CategoryService catService) {
		this.catService = catService;
	}

	@GetMapping("/public/categories")
//	public ResponseEntity<List<Category>> getAllCategories() {
	public ResponseEntity<CategoryResponse> getAllCategories(
			@RequestParam(name="pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber, // pageNumber = 0 => first page
			@RequestParam(name="pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize) {
		return ResponseEntity.ok(catService.getAllCategoriesService(pageNumber, pageSize));
	}
	
	@PostMapping("/admin/categories")
//	public ResponseEntity<String> createCategory(@Valid @RequestBody Category category) {	
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
		CategoryDTO catDTO = catService.createCategoryService(categoryDTO);
		return new ResponseEntity<>(catDTO, HttpStatus.CREATED);
	}
	
	
//	@DeleteMapping("/admin/categories/{categoryId}")
//	public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
//		boolean status = catService.deleteCategoryService(categoryId);
//		if(status)
//			return new ResponseEntity<>("Resource deleted successfully", HttpStatus.OK);
//		else
//			return new ResponseEntity<>("Resource not found", HttpStatus.NOT_FOUND);
//	}
	
	
	@DeleteMapping("/admin/categories/{categoryId}")  // Using 
	public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {
//		try {
//			String str = catService.deleteCategoryService(categoryId);
//			respEntity = new ResponseEntity<>(str, HttpStatus.OK);		
//		} 
//		catch (ResponseStatusException e) {
////			respEntity = new ResponseEntity<>(e.getMessage(), e.getStatusCode()); // Here, error message is as: 404 NOT_FOUND "Resource Not Found"
//			respEntity = new ResponseEntity<>(e.getReason(), e.getStatusCode()); // Here, error message is as: Resource Not Found
//		}
		
		CategoryDTO catDTO = catService.deleteCategoryService(categoryId);
		ResponseEntity<CategoryDTO> respEntity = new ResponseEntity<>(catDTO, HttpStatus.OK);	
		return respEntity;
	}
	
	@PutMapping("/admin/categories/{categoryId}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Long categoryId) {
//		try {
//			Category savedCategory = catService.updateCategoryService(categoryId, category);
//			respEntity = new ResponseEntity<>("Updated the category with category id "+categoryId, HttpStatus.OK);		
//		} 
//		catch (ResponseStatusException e) {
//			respEntity = new ResponseEntity<>(e.getReason(), e.getStatusCode());
//		}
		
		// No need of Exception Handling as we have centralized it
		
		CategoryDTO catDTO = catService.updateCategoryService(categoryId, categoryDTO);
		ResponseEntity<CategoryDTO> respEntity = new ResponseEntity<>(catDTO, HttpStatus.OK);		
		return respEntity;
	}
}
