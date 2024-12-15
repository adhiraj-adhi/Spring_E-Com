package com.ecom.project.services;

import org.springframework.web.multipart.MultipartFile;

import com.ecom.project.model.Product;
import com.ecom.project.payload.APIResponse;
import com.ecom.project.payload.ProductDTO;
import com.ecom.project.payload.ProductResponse;

public interface ProductService {

	ProductDTO addProductService(Long categoryId, Product product);

	ProductResponse getAllProductsService(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

	ProductResponse getProductsByCategoryService(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

	ProductResponse getProductByKeywordService(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

	ProductDTO updateProductService(Long productId, Product product);

	ProductDTO deleteProductService(Long productId);

	APIResponse getProductsCountService();

	ProductDTO updateProductImageService(Long productId, MultipartFile productImage);

}
