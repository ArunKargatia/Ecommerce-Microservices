package com.exercise.orderManagement.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderDetailDTO {

	@NotNull(message = "Product ID must not be null")
	private Long productId;
	
	private String productName;
	
	@Min(value = 1, message = "Quantity must be at least 1")
	private int quantity;
	
	private BigDecimal unitPrice;

}
