package com.exercise.productInventory.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryRevenueDTO {
	
	Long categoryId;
	String categoryName;
	BigDecimal totalRevenue;

}
