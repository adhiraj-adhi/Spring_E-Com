package com.ecom.project.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.project.dao.CategoryRepository;
import com.ecom.project.dao.ProductRepository;
import com.ecom.project.exceptions.ResourceNotFoundException;
import com.ecom.project.model.Category;
import com.ecom.project.model.Product;
import com.ecom.project.payload.APIResponse;
import com.ecom.project.payload.ProductDTO;
import com.ecom.project.payload.ProductResponse;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path; // reading path from application.properties file

	@Override
	public ProductDTO addProductService(Long categoryId, Product product) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", categoryId, "categoryId"));

		product.setCategory(category);
		product.setImage("default.png");
		double specialPrice = product.getPrice() - (product.getPrice() * product.getDiscount() * 0.01);
		product.setSpecialPrice(specialPrice);

		Product savedProduct = productRepository.save(product);

		return modelMapper.map(savedProduct, ProductDTO.class);
	}

	@Override
	public ProductResponse getAllProductsService() {
		List<Product> products = productRepository.findAll();

		List<ProductDTO> productDTOs = products.stream().map(product -> modelMapper.map(product, ProductDTO.class))
				.toList();

		ProductResponse productResponse = new ProductResponse();
		productResponse.setContent(productDTOs);

		return productResponse;
	}

	@Override
	public ProductResponse getProductsByCategoryService(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", categoryId, "categoryId"));

		List<Product> products = productRepository.findByCategoryCategoryId(categoryId);

		List<ProductDTO> productDTOs = products.stream().map(product -> modelMapper.map(product, ProductDTO.class))
				.toList();

		ProductResponse productResponse = new ProductResponse();
		productResponse.setContent(productDTOs);

		return productResponse;
	}

	@Override
	public ProductResponse getProductByKeywordService(String keyword) {
		List<Product> products = productRepository.findByProductNameIgnoreCaseContaining(keyword);
		
		List<ProductDTO> productDTOs = products.stream().map(product -> modelMapper.map(product, ProductDTO.class))
				.toList();

		ProductResponse productResponse = new ProductResponse();
		productResponse.setContent(productDTOs);

		return productResponse;
	}

	@Override
	public ProductDTO updateProductService(Long productId, Product product) {
		Product oldProduct = productRepository.findById(productId)
								.orElseThrow(() -> new ResourceNotFoundException("Product", productId, "productId"));
	
		oldProduct.setProductName(product.getProductName());
		oldProduct.setDescription(product.getDescription());
		oldProduct.setQuantity(product.getQuantity());
		oldProduct.setPrice(product.getPrice());
		oldProduct.setDiscount(product.getDiscount());
		oldProduct.setSpecialPrice(product.getPrice() - (product.getPrice() * product.getDiscount() * 0.01));
		
		Product savedProduct = productRepository.save(oldProduct);
		
		return modelMapper.map(savedProduct, ProductDTO.class);
	}

	@Override
	public ProductDTO deleteProductService(Long productId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", productId, "productId"));
		
		productRepository.deleteById(productId);
		
		return modelMapper.map(product, ProductDTO.class);
	}

	@Override
	public APIResponse getProductsCountService() {
		return new APIResponse("Total products count: "+productRepository.count(), true);
	}

	@Override
	public ProductDTO updateProductImageService(Long productId, MultipartFile productImage) {
		// Get the product from DB
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", productId, "productId"));
		
		/* Upload image to server (for now we will be uploading to server by creating a folder but 
		 * later on we can upload to some media server also say like S3 bucket). Thereafter get
		 *  the file name of uploaded image
		 */
		// String path = "images/";   -> Getting from application.properties file
		String filename = fileService.uploadImage(path, productImage);		
		
		
		// Updating the new file name to the product and save the product
		product.setImage(filename);
		Product savedProduct = productRepository.save(product);
		
		
		// return DTO after mapping product to DTO
		return modelMapper.map(savedProduct, ProductDTO.class);
	}
}
