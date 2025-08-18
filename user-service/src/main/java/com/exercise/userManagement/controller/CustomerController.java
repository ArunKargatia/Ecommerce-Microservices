package com.exercise.userManagement.controller;

import com.exercise.userManagement.dto.CustomerDTO;
import com.exercise.userManagement.dto.UpdatePasswordDTO;
import com.exercise.userManagement.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/profile")
	public ResponseEntity<CustomerDTO> getMyProfile() {
		return ResponseEntity.ok(customerService.getMyProfile()); 
	}
	
	@PutMapping("/password")
	public ResponseEntity<String> updateMyPassword(@Valid @RequestBody UpdatePasswordDTO updatePasswordDto){
		customerService.updatePassword(updatePasswordDto);
		return ResponseEntity.ok("Password updated successfully.");
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<HttpStatus> deleteMyAccount(){
		customerService.deleteMyAccount();
		return ResponseEntity.noContent().build();
	}
}
