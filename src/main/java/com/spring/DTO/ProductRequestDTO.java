package com.spring.DTO;



public class ProductRequestDTO {

	private String name;

	private Double price;

	private Long categoryId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public ProductRequestDTO() {
	}

	public ProductRequestDTO(String name, Double price, Long categoryId) {
		this.name = name;
		this.price = price;
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "ProductRequestDTO{" + "name='" + name + '\'' + ", price=" + price + ", categoryId=" + categoryId + '}';
	}
}

