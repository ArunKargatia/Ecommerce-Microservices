package com.exercise.productInventory.controller;

import com.exercise.productInventory.dto.CategoryRevenueDTO;
import com.exercise.productInventory.dto.ProductDTO;
import com.exercise.productInventory.dto.SupplierStockDTO;
import com.exercise.productInventory.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
	
	@Autowired
	private AnalyticsService analyticsService;
	
	@GetMapping("/products/top")
	public ResponseEntity<List<ProductDTO>> getTopExpensiveProducts(@RequestParam int n){
		return ResponseEntity.ok(analyticsService.getTopNExpensiveProducts(n));
	}
	
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<CategoryRevenueDTO> getRevenueByCategory(@PathVariable Long categoryId){
		return ResponseEntity.ok(analyticsService.getRevenueByCategory(categoryId));
	}
	
	@GetMapping("/supplier/stock/{supplierId}")
	public ResponseEntity<SupplierStockDTO> getTotalStockOfSupplier(@PathVariable Long supplierId){
		return ResponseEntity.ok(analyticsService.getTotalStockOfSupplier(supplierId));
	}
	
	
}
