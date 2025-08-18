package com.exercise.userManagement.service;

import java.util.List;

import com.exercise.userManagement.dto.CustomerDTO;

public interface AdminService {

	List<CustomerDTO> getAllCustomers();
	CustomerDTO getCustomerById(Long customerId);
	void deleteCustomer(Long customerId);
	void updateRole(Long customerId, String newRole);
}
