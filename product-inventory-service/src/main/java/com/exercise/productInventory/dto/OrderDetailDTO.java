package com.exercise.productInventory.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderDetailDTO {

	private Long productId;
	private String productName;
	private int quantity;
	private BigDecimal unitPrice;

}
