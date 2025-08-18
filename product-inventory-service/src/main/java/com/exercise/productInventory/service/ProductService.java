package com.exercise.productInventory.service;

import java.util.List;

import com.exercise.productInventory.dto.ProductDTO;

public interface ProductService {
	
	List<ProductDTO> getAllProducts();
	void addProduct(ProductDTO productDto);
	ProductDTO getProductById(Long productId);
	void updateProduct(Long productId, ProductDTO updatedProductDto);
	void deleteProduct(Long productId);
	
	void increaseProductStock(Long productId, int quantity);
	void decreaseProductStock(Long productId, int quantity);
}
