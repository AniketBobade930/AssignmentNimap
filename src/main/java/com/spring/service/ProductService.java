package com.spring.service;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring.DTO.ProductRequestDTO;
import com.spring.DTO.ProductResponseDTO;
import com.spring.entity.Category;
import com.spring.entity.Product;
import com.spring.repo.CategoryRepo;
import com.spring.repo.ProductRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

	@Autowired
	private ProductRepo productRepository;

	@Autowired
	private CategoryRepo categoryRepository;

	public Page<ProductResponseDTO> getAllProducts(int page, int size) {

		if (page < 0) {
			throw new IllegalArgumentException("Page number cannot be less than 0.");
		}

		Pageable pageable = PageRequest.of(page, size);

		Page<Product> productPage = productRepository.findAll(pageable);

		return productPage.map(this::convertToDTO);
	}

	public List<ProductResponseDTO> getAllProducts() {
		return productRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	public ProductResponseDTO getProductById(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
		return convertToDTO(product);
	}

	public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
		Category category = categoryRepository.findById(productRequestDTO.getCategoryId()).orElseThrow(
				() -> new RuntimeException("Category not found with id: " + productRequestDTO.getCategoryId()));

		Product product = new Product();
		product.setName(productRequestDTO.getName());
		product.setPrice(productRequestDTO.getPrice());
		product.setCategory(category);

		Product savedProduct = productRepository.save(product);
		return convertToDTO(savedProduct);
	}

	public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {
		Product existingProduct = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

		Category category = categoryRepository.findById(productRequestDTO.getCategoryId()).orElseThrow(
				() -> new RuntimeException("Category not found with id: " + productRequestDTO.getCategoryId()));

		existingProduct.setName(productRequestDTO.getName());
		existingProduct.setPrice(productRequestDTO.getPrice());
		existingProduct.setCategory(category);

		Product updatedProduct = productRepository.save(existingProduct);
		return convertToDTO(updatedProduct);
	}

	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}

	private ProductResponseDTO convertToDTO(Product product) {
		ProductResponseDTO responseDTO = new ProductResponseDTO();
		responseDTO.setId(product.getId());
		responseDTO.setName(product.getName());
		responseDTO.setPrice(product.getPrice());

		ProductResponseDTO.CategoryDTO categoryDTO = new ProductResponseDTO.CategoryDTO();
		categoryDTO.setId(product.getCategory().getId());
		categoryDTO.setName(product.getCategory().getName());
		responseDTO.setCategory(categoryDTO);

		return responseDTO;
	}
}
