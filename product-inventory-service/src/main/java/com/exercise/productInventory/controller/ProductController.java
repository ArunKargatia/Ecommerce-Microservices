package com.exercise.productInventory.controller;

import com.exercise.productInventory.dto.ProductDTO;
import com.exercise.productInventory.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/all")
	public ResponseEntity<List<ProductDTO>> getAllProducts(){
		return ResponseEntity.ok(productService.getAllProducts());
	}
	
	@PostMapping
	public ResponseEntity<String> addProduct(@Valid @RequestBody ProductDTO productDto){
		productService.addProduct(productDto);
		return ResponseEntity.status(HttpStatus.CREATED).body("Product Added Successfully.");
	}
	
	@GetMapping("{productId}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId){
		return ResponseEntity.ok(productService.getProductById(productId));
	}
	
	@PutMapping("{productId}")
	public ResponseEntity<String> updateProduct(@PathVariable Long productId, @Valid @RequestBody ProductDTO updatedProductDto){
		productService.updateProduct(productId, updatedProductDto);
		return ResponseEntity.ok("Product Updated Successfully.");
	}
	
	@DeleteMapping("{productId}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long productId){
		productService.deleteProduct(productId);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{productId}/stock/increase")
	public ResponseEntity<String> increaseProductStock(@PathVariable Long productId, @RequestParam int quantity){
		productService.increaseProductStock(productId, quantity);
		return ResponseEntity.ok("Product Stock Increased Successfully");
	}
	
	@PutMapping("/{productId}/stock/decrease")
	public ResponseEntity<String> decreaseProductStock(@PathVariable Long productId, @RequestParam int quantity){
		productService.decreaseProductStock(productId, quantity);
		return ResponseEntity.ok("Product Stock Decreased Successfully");
	}

}
