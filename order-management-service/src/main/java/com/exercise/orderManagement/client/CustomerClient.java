package com.exercise.orderManagement.client;

import com.exercise.orderManagement.dto.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "user-service")
public interface CustomerClient {
	
	@GetMapping("/api/customer/profile")
	CustomerDTO getCustomerProfile();
	
	@GetMapping("/api/admin/customer/{customerId}")
	CustomerDTO getCustomerById(@PathVariable Long customerId);
	
	@GetMapping("/api/admin/customers/all")
	List<CustomerDTO> getAllCustomers();

}