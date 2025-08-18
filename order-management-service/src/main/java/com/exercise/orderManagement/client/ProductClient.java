package com.exercise.orderManagement.client;

import com.exercise.orderManagement.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-inventory-service")
public interface ProductClient {
	
	@GetMapping("/api/products/{productId}")
	ProductDTO getProductById(@PathVariable Long productId);
	
	@PutMapping("api/products/{productId}/stock/increase")
	void increaseProductStock(@PathVariable Long productId, @RequestParam int quantity);
	
	@PutMapping("api/products/{productId}/stock/decrease")
	void decreaseProductStock(@PathVariable Long productId, @RequestParam int quantity);

}
