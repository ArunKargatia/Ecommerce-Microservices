package com.exercise.userManagement.service;

import com.exercise.userManagement.dto.CustomerDTO;
import com.exercise.userManagement.dto.UpdatePasswordDTO;

public interface CustomerService {
	
	CustomerDTO getMyProfile();
	void updatePassword(UpdatePasswordDTO updatePasswordDto);
	void deleteMyAccount();
	
}
