package com.exercise.userManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.userManagement.dto.LoginDTO;
import com.exercise.userManagement.dto.LoginResponseDTO;
import com.exercise.userManagement.dto.RegisterDTO;
import com.exercise.userManagement.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	
	@PostMapping("/register")
	public ResponseEntity<String> registerCustomer(@Valid @RequestBody RegisterDTO registerDto){
		authService.registerCustomer(registerDto);
		return ResponseEntity.status(HttpStatus.CREATED).body("Customer registered successfully");
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO loginDto){
		LoginResponseDTO response = authService.login(loginDto);
		return ResponseEntity.ok(response);
	}
}

