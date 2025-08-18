package com.exercise.userManagement.service.impl;

import com.exercise.userManagement.dto.CustomerDTO;
import com.exercise.userManagement.dto.CustomerMapper;
import com.exercise.userManagement.entity.Customer;
import com.exercise.userManagement.repository.CustomerRepository;
import com.exercise.userManagement.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public List<CustomerDTO> getAllCustomers() {
		return customerRepository.findAll().stream().map(customer -> CustomerMapper.toDTO(customer)).toList();
	}

	@Override
	public CustomerDTO getCustomerById(Long customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with ID: " + customerId));
		return CustomerMapper.toDTO(customer);
	}

	@Override
	public void deleteCustomer(Long customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with ID: " + customerId));
		customerRepository.delete(customer);
	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public void updateRole(Long customerId, String newRole) {
		Customer customer = customerRepository.findById(customerId)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

		 if (!newRole.equalsIgnoreCase("ADMIN") && !newRole.equalsIgnoreCase("USER")) {
		        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid role value");
		    }
		
		customer.setRole(newRole.toUpperCase());
		customerRepository.save(customer);
	}
}
