package com.spring.service;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring.DTO.CategoryDTO;
import com.spring.DTO.ProductResponseDTO;
import com.spring.entity.Category;
import com.spring.entity.Product;
import com.spring.repo.CategoryRepo;
import com.spring.repo.ProductRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepo categoryRepository;

	@Autowired
	private ProductRepo productRepository;

	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		Category category = new Category();
		category.setName(categoryDTO.getName());

		List<Product> products = categoryDTO.getProducts().stream()
				.map(productDTO -> convertToProductEntity(productDTO, category)).collect(Collectors.toList());
		category.setProducts(products);

		Category savedCategory = categoryRepository.save(category);
		return mapToCategoryDTO(savedCategory);
	}

	public Page<CategoryDTO> getAllCategories(int page, int size) {

		if (page < 0) {
			throw new IllegalArgumentException("Page number cannot be less than 0.");
		}

		Pageable pageable = PageRequest.of(page, size);

		Page<Category> categoryPage = categoryRepository.findAll(pageable);

		if (page > categoryPage.getTotalPages()) {
			throw new IllegalArgumentException("Requested page exceeds total pages available.");
		}

		return categoryPage.map(this::mapToCategoryDTO);
	}

	public CategoryDTO getCategoryById(Long id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));
		return mapToCategoryDTO(category);
	}

	public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
		Category existingCategory = categoryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));

		existingCategory.setName(categoryDTO.getName());

		existingCategory.getProducts().clear();
		List<Product> updatedProducts = categoryDTO.getProducts().stream()
				.map(productDTO -> convertToProductEntity(productDTO, existingCategory)).collect(Collectors.toList());
		existingCategory.setProducts(updatedProducts);

		Category updatedCategory = categoryRepository.save(existingCategory);
		return mapToCategoryDTO(updatedCategory);
	}

	public void deleteCategory(Long id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));

		if (!category.getProducts().isEmpty()) {
			productRepository.deleteAll(category.getProducts());
		}

		categoryRepository.delete(category);
	}

	private CategoryDTO mapToCategoryDTO(Category category) {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(category.getId());
		categoryDTO.setName(category.getName());

		List<ProductResponseDTO> productDTOs = category.getProducts().stream().map(product -> {
			ProductResponseDTO productResponseDTO = new ProductResponseDTO();
			productResponseDTO.setId(product.getId());
			productResponseDTO.setName(product.getName());
			productResponseDTO.setPrice(product.getPrice());

			ProductResponseDTO.CategoryDTO productCategoryDTO = new ProductResponseDTO.CategoryDTO();
			productCategoryDTO.setId(category.getId());
			productCategoryDTO.setName(category.getName());
			productResponseDTO.setCategory(productCategoryDTO);

			return productResponseDTO;
		}).collect(Collectors.toList());

		categoryDTO.setProducts(productDTOs);
		return categoryDTO;
	}

	private Product convertToProductEntity(ProductResponseDTO productDTO, Category category) {
		Product product = new Product();
		product.setName(productDTO.getName());
		product.setPrice(productDTO.getPrice());
		product.setCategory(category);
		return product;
	}

}
