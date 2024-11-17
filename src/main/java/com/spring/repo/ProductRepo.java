package com.spring.repo;



import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.Product;



public interface ProductRepo extends JpaRepository<Product, Long> {
}