package com.exercise.orderManagement.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrderDTO {

	private Long orderId;
	
	@NotNull(message = "Order date must not be null")
	@PastOrPresent(message = "Order date cannot be in the future")
	private LocalDate orderDate;
	
	private BigDecimal totalAmount;
	
	private Long customerId;

	@NotNull(message = "Order details must not be null")
    @Size(min = 1, message = "At least one order detail is required")
    @Valid
	private List<OrderDetailDTO> orderDetails;

}
