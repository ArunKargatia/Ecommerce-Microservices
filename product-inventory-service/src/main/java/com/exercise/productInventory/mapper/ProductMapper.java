package com.exercise.productInventory.mapper;

import com.exercise.productInventory.dto.ProductDTO;
import com.exercise.productInventory.entity.Product;

public class ProductMapper {
	
	public static ProductDTO toDTO(Product product) {
		
		if(product == null) return null;
		
		ProductDTO dto = new ProductDTO();
		dto.setProductId(product.getProductId());
		dto.setProductName(product.getProductName());
		dto.setUnitPrice(product.getUnitPrice());
		dto.setStock(product.getStock());
		
		if(product.getCategory() != null) {
			dto.setCategoryId(product.getCategory().getCategoryId());
		}
		
		if(product.getSupplier() != null) {
			dto.setSupplierId(product.getSupplier().getSupplierId());
		}
		return dto;
	}
	
	public static Product toEntity(ProductDTO dto) {
		
		if(dto == null) return null;
		
		Product product = new Product();
		product.setProductId(dto.getProductId());
		product.setProductName(dto.getProductName());
		product.setUnitPrice(dto.getUnitPrice());
		product.setStock(dto.getStock());
		
		return product;
	}
}
