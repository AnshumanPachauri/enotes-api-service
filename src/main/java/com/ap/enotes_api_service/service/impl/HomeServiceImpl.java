package com.ap.enotes_api_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ap.enotes_api_service.entity.AccountStatus;
import com.ap.enotes_api_service.entity.User;
import com.ap.enotes_api_service.exception.ResourceNotFoundException;
import com.ap.enotes_api_service.repository.UserRepository;
import com.ap.enotes_api_service.service.HomeService;

@Component
public class HomeServiceImpl implements HomeService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Boolean verifyAccount(Integer userId, String VerificationCode) throws Exception {
		// TODO Auto-generated method stub
		
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Invalid User"));
		
		if(user.getStatus().getVerificationCode().equals(VerificationCode)) {
			AccountStatus status = user.getStatus();
			status.setIsActive(true);
			status.setVerificationCode(null);
			user.setStatus(status);
			userRepository.save(user);
			return true;
		}
		
		return false;
	}

}
