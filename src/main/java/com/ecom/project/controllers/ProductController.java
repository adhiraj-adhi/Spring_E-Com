package com.ecom.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.multipart.MultipartFile;

import com.ecom.project.config.AppConstants;
import com.ecom.project.model.Product;
import com.ecom.project.payload.APIResponse;
import com.ecom.project.payload.ProductDTO;
import com.ecom.project.payload.ProductResponse;
import com.ecom.project.services.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	// Add a product
	@PostMapping("/admin/categories/{categoryId}/product")
	public ResponseEntity<ProductDTO> addProduct(@PathVariable Long categoryId, 
			@Valid @RequestBody Product product
			) {
		ProductDTO productDTO = productService.addProductService(categoryId, product);
		return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
	}
	
	// Get all products
	@GetMapping("/public/products")
	public ResponseEntity<ProductResponse> getAllProducts(
			@RequestParam(name="pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
			@RequestParam(name="pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
			@RequestParam(name="sortBy", defaultValue = AppConstants.SORT_PRODUCT_BY) String sortBy,
			@RequestParam(name="sortOrder", defaultValue = AppConstants.SORT_ORDER) String sortOrder) {
		return ResponseEntity.ok(productService.getAllProductsService(pageNumber, pageSize, sortBy, sortOrder));
	}
	
	// Get all products of a category
	@GetMapping("/public/categories/{categoryId}/products")
	public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId,
			@RequestParam(name="pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
			@RequestParam(name="pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
			@RequestParam(name="sortBy", defaultValue = AppConstants.SORT_PRODUCT_BY) String sortBy,
			@RequestParam(name="sortOrder", defaultValue = AppConstants.SORT_ORDER) String sortOrder){
		return ResponseEntity.ok(productService.getProductsByCategoryService(categoryId, pageNumber, pageSize, sortBy, sortOrder));
	}
	
	// Get all products with a keyword
	@GetMapping("/public/products/keyword/{keyword}")
	public ResponseEntity<ProductResponse> getProductByKeyword(@PathVariable String keyword,
			@RequestParam(name="pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
			@RequestParam(name="pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
			@RequestParam(name="sortBy", defaultValue = AppConstants.SORT_PRODUCT_BY) String sortBy,
			@RequestParam(name="sortOrder", defaultValue = AppConstants.SORT_ORDER) String sortOrder) {
		return new ResponseEntity<ProductResponse>(productService.getProductByKeywordService(keyword, pageNumber, pageSize, sortBy, sortOrder), HttpStatus.FOUND);
	}
	
	
	// Update a product (except image)
	@PutMapping("/admin/products/{productId}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId, @Valid @RequestBody Product product) {
		return ResponseEntity.ok(productService.updateProductService(productId, product));
	}	
	
	
	// Delete a product
	@DeleteMapping("/admin/products/{productId}")
	public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId) {
		return ResponseEntity.ok(productService.deleteProductService(productId));
	}
	
	
	// Update a product image
	@PutMapping("/admin/products/{productId}/image")
	public ResponseEntity<ProductDTO> updateProductImage(
			@PathVariable Long productId,
			@RequestParam("Image") MultipartFile productImage) {
		ProductDTO updatedProduct = productService.updateProductImageService(productId, productImage);
		return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
	}
	
	
	// Get Products By Seller
	
	
	
	
	// Get Product Count
//	@GetMapping("/admin/products/count")
//	public ResponseEntity<Long> getProductsCount() {
//		return ResponseEntity.ok(productService.getProductsCountService());
//	}
	@GetMapping("/admin/products/count")
	public ResponseEntity<APIResponse> getProductsCount() {
		return ResponseEntity.ok(productService.getProductsCountService());
	}
}
