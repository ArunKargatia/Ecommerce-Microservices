package com.exercise.userManagement.service;

import com.exercise.userManagement.dto.CustomerDTO;

import java.util.List;

public interface AdminService {

	List<CustomerDTO> getAllCustomers();
	CustomerDTO getCustomerById(Long customerId);
	void deleteCustomer(Long customerId);
	void updateRole(Long customerId, String newRole);
}
