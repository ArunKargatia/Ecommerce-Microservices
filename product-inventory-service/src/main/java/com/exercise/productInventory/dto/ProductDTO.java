package com.exercise.productInventory.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductDTO {
	
	private Long productId;
	
	@NotBlank(message = "Product name must not be blank")
	private String productName;
	
	@NotNull(message = "Unit price must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than 0")
	private BigDecimal unitPrice;
	
	@NotNull(message = "Stock must not be null")
	@Min(value = 0, message = "Stock cannot be negative")
	private Long stock;
	
	@NotNull(message = "Category ID must not be null")
	private Long categoryId;
	 
	@NotNull(message = "Supplier ID must not be null") 
	private Long supplierId;

}
