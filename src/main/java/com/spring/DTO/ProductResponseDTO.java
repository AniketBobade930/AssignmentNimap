package com.spring.DTO;


public class ProductResponseDTO {
	private Long id;
	private String name;
	private Double price;
	private CategoryDTO category;

	public ProductResponseDTO() {
	}

	public ProductResponseDTO(Long id, String name, Double price, CategoryDTO category) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.category = category;
	}

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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public CategoryDTO getCategory() {
		return category;
	}

	public void setCategory(CategoryDTO category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "ProductResponseDTO{" + "id=" + id + ", name='" + name + '\'' + ", price=" + price + ", category="
				+ category + '}';
	}

	public static class CategoryDTO {
		private Long id;
		private String name;

		public CategoryDTO() {
		}

		public CategoryDTO(Long id, String name) {
			this.id = id;
			this.name = name;
		}

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

		@Override
		public String toString() {
			return "CategoryDTO{" + "id=" + id + ", name='" + name + '\'' + '}';
		}
	}
}
