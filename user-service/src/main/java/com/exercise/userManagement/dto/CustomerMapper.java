package com.exercise.userManagement.dto;

import com.exercise.userManagement.entity.Customer;

public class CustomerMapper {

	public static CustomerDTO toDTO(Customer customer) {

		if (customer == null)
			return null;

		CustomerDTO dto = new CustomerDTO();
		dto.setCustomerId(customer.getCustomerId());
		dto.setCustomerName(customer.getCustomerName());
		dto.setEmail(customer.getEmail());
		dto.setPhoneNumber(customer.getPhoneNumber());
		dto.setRole(customer.getRole());

		return dto;
	}

	public static Customer toEntity(CustomerDTO dto) {

		if (dto == null)
			return null;

		Customer customer = new Customer();
		customer.setCustomerId(dto.getCustomerId());
		customer.setCustomerName(dto.getCustomerName());
		customer.setEmail(dto.getEmail());
		customer.setPhoneNumber(dto.getPhoneNumber());

		return customer;
	}

}
