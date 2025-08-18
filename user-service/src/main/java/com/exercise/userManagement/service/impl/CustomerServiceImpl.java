package com.exercise.userManagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.exercise.userManagement.dto.CustomerDTO;
import com.exercise.userManagement.dto.CustomerMapper;
import com.exercise.userManagement.dto.UpdatePasswordDTO;
import com.exercise.userManagement.entity.Customer;
import com.exercise.userManagement.repository.CustomerRepository;
import com.exercise.userManagement.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public CustomerDTO getMyProfile() {
        Long customerId = getCurrentCustomerId();
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with ID: " + customerId));
		
		return CustomerMapper.toDTO(customer);
	}

	@Override
	public void updatePassword(UpdatePasswordDTO updatePasswordDto) {
		Long customerId = getCurrentCustomerId();
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with ID: " + customerId));
		
		if (!passwordEncoder.matches(updatePasswordDto.getOldPassword(), customer.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old password is incorrect");
        }
		
		if (updatePasswordDto.getNewPassword().equals(updatePasswordDto.getOldPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password cannot be the same as old password");
        }
		
		customer.setPassword(passwordEncoder.encode(updatePasswordDto.getNewPassword()));
		customerRepository.save(customer);
	}

	@Override
	public void deleteMyAccount() {
		Long customerId = getCurrentCustomerId();
        if (!customerRepository.existsById(customerId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
                    "Customer not found with ID: " + customerId);
        }
        customerRepository.deleteById(customerId);
	}
	
	private Long getCurrentCustomerId() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication != null && authentication.isAuthenticated()) {
	        String email = authentication.getName(); 
	        return customerRepository.findByEmail(email)
	                .map(Customer::getCustomerId)
	                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
	    }
	    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No authenticated user found");
	}

}
