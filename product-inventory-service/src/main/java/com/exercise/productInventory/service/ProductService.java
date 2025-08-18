package com.exercise.productInventory.service;

import com.exercise.productInventory.dto.ProductDTO;

import java.util.List;

public interface ProductService {
	
	List<ProductDTO> getAllProducts();
	void addProduct(ProductDTO productDto);
	ProductDTO getProductById(Long productId);
	void updateProduct(Long productId, ProductDTO updatedProductDto);
	void deleteProduct(Long productId);
	
	void increaseProductStock(Long productId, int quantity);
	void decreaseProductStock(Long productId, int quantity);
}
