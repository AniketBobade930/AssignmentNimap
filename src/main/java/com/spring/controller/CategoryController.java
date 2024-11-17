package com.spring.controller;



import com.spring.DTO.CategoryDTO;
import com.spring.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping
	public ResponseEntity<Page<CategoryDTO>> getAllCategories(@RequestParam(value ="pagenumber",defaultValue = "1") int page,
			@RequestParam(value="pagesize", defaultValue = "4") int size) {

		if (page < 1) {
			throw new IllegalArgumentException("Page number must be greater than or equal to 1.");
		}

		Page<CategoryDTO> categories = categoryService.getAllCategories(page - 1, size); // subtract 1 because
																							// PageRequest starts from 0
		return ResponseEntity.ok(categories);
	}

	@PostMapping
	public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
		CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
		return ResponseEntity.ok(createdCategory);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
		CategoryDTO category = categoryService.getCategoryById(id);
		return ResponseEntity.ok(category);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
		CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
		return ResponseEntity.ok(updatedCategory);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
		categoryService.deleteCategory(id);
		return ResponseEntity.ok("Category with ID " + id + " deleted successfully.");
	}
}
