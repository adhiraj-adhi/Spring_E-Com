package com.ecom.project.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ecom.project.model.Category;
import com.ecom.project.services.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController {
	
	private CategoryService catService;
	
	public CategoryController(CategoryService catService) {
		this.catService = catService;
	}

	@GetMapping("/public/categories")
	public ResponseEntity<List<Category>> getAllCategories() {
		return ResponseEntity.ok(catService.getAllCategoriesService());
	}
	
	@PostMapping("/admin/categories")
	public ResponseEntity<String> createCategory(@RequestBody Category category) {
		boolean status = catService.createCategoryService(category);
		if(status)
			return new ResponseEntity<>("Resource created", HttpStatus.CREATED);
		else
			return new ResponseEntity<>("Resource creation failed", HttpStatus.INTERNAL_SERVER_ERROR);
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
	public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
		ResponseEntity<String> respEntity = null;
		try {
			String str = catService.deleteCategoryService(categoryId);
			respEntity = new ResponseEntity<>(str, HttpStatus.OK);		
		} 
		catch (ResponseStatusException e) {
//			respEntity = new ResponseEntity<>(e.getMessage(), e.getStatusCode()); // Here, error message is as: 404 NOT_FOUND "Resource Not Found"
			respEntity = new ResponseEntity<>(e.getReason(), e.getStatusCode()); // Here, error message is as: Resource Not Found
		}
		return respEntity;
	}
	
	@PutMapping("/admin/categories/{categoryId}")
	public ResponseEntity<String> updateCategory(@RequestBody Category category, @PathVariable Long categoryId) {
		ResponseEntity<String> respEntity = null;
		try {
			Category savedCategory = catService.updateCategoryService(categoryId, category);
			respEntity = new ResponseEntity<>("Updated the category with category id "+categoryId, HttpStatus.OK);		
		} 
		catch (ResponseStatusException e) {
			respEntity = new ResponseEntity<>(e.getReason(), e.getStatusCode());
		}
		return respEntity;
	}
}
