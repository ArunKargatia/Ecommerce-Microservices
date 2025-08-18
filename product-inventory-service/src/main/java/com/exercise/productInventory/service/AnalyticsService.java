package com.exercise.productInventory.service;

import com.exercise.productInventory.dto.CategoryRevenueDTO;
import com.exercise.productInventory.dto.ProductDTO;
import com.exercise.productInventory.dto.SupplierStockDTO;

import java.util.List;

public interface AnalyticsService {
	
	List<ProductDTO> getTopNExpensiveProducts(int n);
	
	CategoryRevenueDTO getRevenueByCategory(Long categoryId);
	
	SupplierStockDTO getTotalStockOfSupplier(Long supplierId);

}
