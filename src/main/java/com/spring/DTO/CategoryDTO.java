package com.spring.DTO;



import java.util.List;

public class CategoryDTO {

	private Long id;
	private String name;
	private List<ProductResponseDTO> products;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ProductResponseDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductResponseDTO> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "CategoryDTO{" + "id=" + id + ", name='" + name + '\'' + ", products=" + products + '}';
	}
}
