package com.ap.enotes_api_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ap.enotes_api_service.dto.PasswordChangeRequest;
import com.ap.enotes_api_service.entity.User;
import com.ap.enotes_api_service.repository.UserRepository;
import com.ap.enotes_api_service.service.UserService;
import com.ap.enotes_api_service.utils.CommonUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void ChangePassword(PasswordChangeRequest passwordChangeRequest) {
		
		User loggedInUser = CommonUtil.getLoggedInUser();
		
		if(!passwordEncoder.matches(loggedInUser.getPassword(), passwordChangeRequest.toString())) {
			throw new IllegalArgumentException("Your Old Password is incorrect!!!");
		}
		
		loggedInUser.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
		userRepository.save(loggedInUser);
	}

}
