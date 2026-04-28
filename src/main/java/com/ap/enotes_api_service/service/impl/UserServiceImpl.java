package com.ap.enotes_api_service.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ap.enotes_api_service.dto.EmailRequest;
import com.ap.enotes_api_service.dto.PasswordChangeRequest;
import com.ap.enotes_api_service.entity.User;
import com.ap.enotes_api_service.exception.ResourceNotFoundException;
import com.ap.enotes_api_service.repository.UserRepository;
import com.ap.enotes_api_service.service.UserService;
import com.ap.enotes_api_service.utils.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmailService emailService;
	
	@Override
	public void ChangePassword(PasswordChangeRequest passwordChangeRequest) {
		
		User loggedInUser = CommonUtil.getLoggedInUser();
		
		if(!bCryptPasswordEncoder.matches(passwordChangeRequest.getOldPassword(), loggedInUser.getPassword())) {
			throw new IllegalArgumentException("Your Old Password is incorrect!!!");
		}
		
		loggedInUser.setPassword(bCryptPasswordEncoder.encode(passwordChangeRequest.getNewPassword()));
		userRepository.save(loggedInUser);
	}

	@Override
	public void sendEmailPasswordReset(String email, HttpServletRequest servletRequest) throws Exception {
		
		User userByEmail = userRepository.findByEmail(email);
		
		if(ObjectUtils.isEmpty(userByEmail)) {
			throw new ResourceNotFoundException("User with Email :- "+email+" Not FOund.");
		}
		
		//Generating password Reset TOken.
		
		String resetToken = UUID.randomUUID().toString();
		userByEmail.getStatus().setPasswordResetToken(resetToken);
		User updatedUser = userRepository.save(userByEmail);
		
		String requestUrl = CommonUtil.getRequestUrl(servletRequest);
		sendEmailRequest(updatedUser, requestUrl);
	}

	private void sendEmailRequest(User updatedUser, String requestUrl) throws Exception {
		
		String message = "Hi, <b>"+updatedUser.getFirstName()+" </b> "
				+ "<br> You have requested to reset your password.<br>"
				+ "<br> Click the link below to verify and change your password.<br>"
				+ "<a href='"+requestUrl+"/api/v1/home/verify-password-link?id=" 
				+ updatedUser.getId() 
				+ "&VC=" 
				+ updatedUser.getStatus().getPasswordResetToken() 
				+ "'>Click Here</a><br><br>"
				+ "Thanks, <br> Enotes.anshuman.com"; 
		
		EmailRequest emailRequest = EmailRequest.builder()
				.to(updatedUser.getEmail())
				.subject("Passowrd Reset Verification Link.")
				.title("Password Reset.")
				.message(message)
				.build();
		emailService.send(emailRequest);

	}

}
