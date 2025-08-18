package com.exercise.userManagement.service;

import com.exercise.userManagement.dto.LoginDTO;
import com.exercise.userManagement.dto.LoginResponseDTO;
import com.exercise.userManagement.dto.RegisterDTO;

public interface AuthService {
	
	void registerCustomer(RegisterDTO registerDto);
	LoginResponseDTO login(LoginDTO loginDto);

}
