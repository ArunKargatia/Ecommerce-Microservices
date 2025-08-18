package com.exercise.orderManagement.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerOrderValueDTO {
	
	Long customerId;
	String customerName;
	BigDecimal averageOrderValue;

}
