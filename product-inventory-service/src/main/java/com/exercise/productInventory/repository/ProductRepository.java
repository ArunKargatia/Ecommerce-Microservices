package com.exercise.productInventory.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercise.productInventory.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	Optional<Product> findByProductName(String productName);
	
	List<Product> findByCategoryCategoryId(Long categoryId);
	
	List<Product> findBySupplierSupplierId(Long supplierId);
			
}
