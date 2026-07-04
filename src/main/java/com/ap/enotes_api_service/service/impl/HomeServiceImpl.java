package com.ap.enotes_api_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ap.enotes_api_service.entity.AccountStatus;
import com.ap.enotes_api_service.entity.User;
import com.ap.enotes_api_service.exception.ResourceNotFoundException;
import com.ap.enotes_api_service.exception.SuccessException;
import com.ap.enotes_api_service.repository.UserRepository;
import com.ap.enotes_api_service.service.HomeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HomeServiceImpl implements HomeService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Boolean verifyAccount(Integer userId, String VerificationCode) throws Exception {
		// TODO Auto-generated method stub
		log.info("--- [enotes-api-service] [HomeServiceImpl] VerifyAccount() : Service Started.");
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Invalid User"));
		
		if(user.getStatus().getVerificationCode() == null) {
			throw new SuccessException("Account already verified");
		}
		
		if(user.getStatus().getVerificationCode().equals(VerificationCode)) {
			AccountStatus status = user.getStatus();
			status.setIsActive(true);
			status.setVerificationCode(null);
			user.setStatus(status);
			userRepository.save(user);
			log.info("--- [enotes-api-service] [HomeServiceImpl] VerifyAccount() : Service ending.");
			return true;
		}
		log.info("--- [enotes-api-service] [HomeServiceImpl] VerifyAccount() : Service ending.");
		return false;
	}

}
