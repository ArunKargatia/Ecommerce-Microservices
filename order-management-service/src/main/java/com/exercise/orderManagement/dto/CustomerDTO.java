package com.exercise.orderManagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

	private Long customerId;
	
	@NotBlank(message = "Customer name must not be blank")
	private String customerName;
	
	@NotBlank(message = "Email must not be blank")
    @Email(message = "Invalid email format")
	private String email;
	
	@NotBlank(message = "Phone number must not be blank")
	@Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 digits")
	private String phoneNumber;

	private String role;
}
