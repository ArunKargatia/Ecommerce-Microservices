package com.exercise.orderManagement.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.exercise.orderManagement.dto.CustomerDTO;

@FeignClient(name = "user-service", url = "http://localhost:8083")
public interface CustomerClient {
	
	@GetMapping("/api/customer/profile")
	CustomerDTO getCustomerProfile();
	
	@GetMapping("/api/admin/customer/{customerId}")
	CustomerDTO getCustomerById(@PathVariable Long customerId);
	
	@GetMapping("/api/admin/customers/all")
	List<CustomerDTO> getAllCustomers();

}