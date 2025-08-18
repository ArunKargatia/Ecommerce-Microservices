package com.exercise.orderManagement.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.exercise.orderManagement.dto.ProductDTO;

@FeignClient(name = "product-inventory-service", url = "http://localhost:8081")
public interface ProductClient {
	
	@GetMapping("/api/products/{productId}")
	ProductDTO getProductById(@PathVariable Long productId);
	
	@PutMapping("api/products/{productId}/stock/increase")
	void increaseProductStock(@PathVariable Long productId, @RequestParam int quantity);
	
	@PutMapping("api/products/{productId}/stock/decrease")
	void decreaseProductStock(@PathVariable Long productId, @RequestParam int quantity);
}
