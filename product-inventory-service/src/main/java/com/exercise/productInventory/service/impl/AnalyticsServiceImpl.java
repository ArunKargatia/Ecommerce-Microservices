package com.exercise.productInventory.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.exercise.productInventory.client.OrderClient;
import com.exercise.productInventory.dto.CategoryRevenueDTO;
import com.exercise.productInventory.dto.OrderDetailDTO;
import com.exercise.productInventory.dto.ProductDTO;
import com.exercise.productInventory.dto.SupplierStockDTO;
import com.exercise.productInventory.entity.Product;
import com.exercise.productInventory.mapper.ProductMapper;
import com.exercise.productInventory.repository.ProductRepository;
import com.exercise.productInventory.service.AnalyticsService;

@Service
public class AnalyticsServiceImpl implements AnalyticsService{

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderClient orderClient;
	
	@Override
	public List<ProductDTO> getTopNExpensiveProducts(int n) {
		List<Product> products = productRepository.findAll();
		
		return products.stream()
				.sorted((p1, p2) -> p2.getUnitPrice().compareTo(p1.getUnitPrice()))
				.limit(n)
				.map(product -> ProductMapper.toDTO(product))
				.toList();
	}

	@Override
	public CategoryRevenueDTO getRevenueByCategory(Long categoryId) {
		List<OrderDetailDTO> orderDetails = orderClient.getAllOrderDetails();
		
		List<Product> products = productRepository.findByCategoryCategoryId(categoryId);
		
		if (products.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No products found for category ID: " + categoryId);
        }
		
		Set<Long> productIds = products.stream()
		        .map(product -> product.getProductId())
		        .collect(Collectors.toSet());
		
		String categoryName = products.isEmpty() ? "Unknown" : products.get(0).getCategory().getCategoryName();
		
		BigDecimal totalRevenue = orderDetails.stream()
				.filter(orderDetail -> productIds.contains(orderDetail.getProductId()))
				.map(orderDetail -> orderDetail.getUnitPrice().multiply(BigDecimal.valueOf(orderDetail.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		return new CategoryRevenueDTO(categoryId, categoryName, totalRevenue);
	}

	@Override
	public SupplierStockDTO getTotalStockOfSupplier(Long supplierId) {
		List<Product> products = productRepository.findBySupplierSupplierId(supplierId);
		
		 if (products.isEmpty()) {
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No products found for supplier ID: " + supplierId);
	        }
		 
		 Long totalStock = products.stream()
				 .mapToLong(product -> product.getStock())
				 .sum();
		 
		 String supplierName = products.get(0).getSupplier().getSupplierName();
		
		return new SupplierStockDTO(supplierId, supplierName, totalStock);
	}

}
