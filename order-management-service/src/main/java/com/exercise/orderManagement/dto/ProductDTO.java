package com.exercise.orderManagement.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductDTO {
	
	private Long productId;
    private String productName;
    private BigDecimal unitPrice;
    private Long stock;

}
