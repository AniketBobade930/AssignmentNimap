package com.spring.controller;






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.DTO.ProductRequestDTO;
import com.spring.DTO.ProductResponseDTO;
import com.spring.service.ProductService;


@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping
	public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(@RequestParam(value = "pagenumber",defaultValue = "1") int page,
			@RequestParam(value = "pagesize",defaultValue = "10") int size) {

		if (page < 1) {
			throw new IllegalArgumentException("Page number can be greater than 0.");
		}

		Page<ProductResponseDTO> products = productService.getAllProducts(page -1, size);
		return ResponseEntity.ok(products);
	}

	@PostMapping
	public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productRequestDTO) {
		ProductResponseDTO createdProduct = productService.createProduct(productRequestDTO);
		return ResponseEntity.ok(createdProduct);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
		ProductResponseDTO product = productService.getProductById(id);
		return ResponseEntity.ok(product);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id,
			@RequestBody ProductRequestDTO productRequestDTO) {
		ProductResponseDTO updatedProduct = productService.updateProduct(id, productRequestDTO);
		return ResponseEntity.ok(updatedProduct);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}
}
