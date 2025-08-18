package com.exercise.userManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

	private Long customerId;
	private String customerName;
	private String email;
	private String phoneNumber;
	private String role;

}
