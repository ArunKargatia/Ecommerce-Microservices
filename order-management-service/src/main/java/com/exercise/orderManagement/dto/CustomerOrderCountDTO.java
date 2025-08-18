package com.exercise.orderManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerOrderCountDTO {
	
	private Long customerId;
	private String customerName;
	private Long orderCount;

}
