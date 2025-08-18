package com.exercise.productInventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SupplierDTO {

	private Long supplierId;
	
	@NotBlank(message = "Supplier name must not be blank")
	private String supplierName;
	
	@NotBlank(message = "Contact must not be blank")
    @Size(min = 10, max = 15, message = "Contact must be between 10 and 15 characters")
	private String contact;

}
