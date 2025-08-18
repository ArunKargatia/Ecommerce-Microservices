package com.exercise.userManagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.exercise.userManagement.config.jwt.JwtUtil;
import com.exercise.userManagement.dto.LoginDTO;
import com.exercise.userManagement.dto.LoginResponseDTO;
import com.exercise.userManagement.dto.RegisterDTO;
import com.exercise.userManagement.entity.Customer;
import com.exercise.userManagement.repository.CustomerRepository;
import com.exercise.userManagement.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	public void registerCustomer(RegisterDTO registerDto) {
		
		 customerRepository.findByEmail(registerDto.getEmail())
	        .ifPresent(c -> {
	        	 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already registered");
	        });
		 
		 Customer customer = new Customer();
		    customer.setCustomerName(registerDto.getName());
		    customer.setEmail(registerDto.getEmail());
		    customer.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		    customer.setRole("USER");

		 customerRepository.save(customer);
	}
	
	@Override
	public LoginResponseDTO login(LoginDTO loginDto) {
		
		Customer customer = customerRepository.findByEmail(loginDto.getEmail())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));
		
		if (!passwordEncoder.matches(loginDto.getPassword(), customer.getPassword())) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
		}
		
		String token = jwtUtil.generateToken(customer.getCustomerId(), customer.getEmail(), customer.getRole());
		
		return new LoginResponseDTO(token, customer.getEmail(), customer.getRole());
	}

}
