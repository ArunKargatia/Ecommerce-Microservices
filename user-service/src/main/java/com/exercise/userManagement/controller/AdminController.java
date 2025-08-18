package com.exercise.userManagement.controller;

import com.exercise.userManagement.dto.CustomerDTO;
import com.exercise.userManagement.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@GetMapping("/customers/all")
	public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
		return ResponseEntity.ok(adminService.getAllCustomers());
	}
	
	@GetMapping("/customer/{customerId}")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long customerId) {
		return ResponseEntity.ok(adminService.getCustomerById(customerId));
	}

	@DeleteMapping("/customer/{customerId}")
	public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable Long customerId) {
		adminService.deleteCustomer(customerId);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/customer/{customerId}/role")
	public ResponseEntity<String> updateRole(@PathVariable Long customerId, @RequestParam String newRole){
		adminService.updateRole(customerId, newRole);
		return ResponseEntity.ok("Role updated successfully.");
	}
}
