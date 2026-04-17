package com.ap.enotes_api_service.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ap.enotes_api_service.dto.EmailRequest;
import com.ap.enotes_api_service.dto.UserDto;
import com.ap.enotes_api_service.entity.AccountStatus;
import com.ap.enotes_api_service.entity.Role;
import com.ap.enotes_api_service.entity.User;
import com.ap.enotes_api_service.repository.RoleRepository;
import com.ap.enotes_api_service.repository.UserRepository;
import com.ap.enotes_api_service.service.UserService;
import com.ap.enotes_api_service.utils.Validation;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private Validation validation; 
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private EmailService emailService;
	
	@Override
	public Boolean register(UserDto userDto) throws Exception {
		
		validation.userValidation(userDto);
		
		User mappedUser = modelMapper.map(userDto, User.class);
		setRoles(userDto, mappedUser);
		
		AccountStatus status = AccountStatus.builder()
				.isActive(false)
				.verificationCode(UUID.randomUUID().toString())
				.build();
		mappedUser.setStatus(status);
		
		User savedUser = userRepository.save(mappedUser);
		
		if(ObjectUtils.isEmpty(savedUser)) {
			return false;
		}
		sendEmail(savedUser);
		return true;
	}

	private void sendEmail(User savedUser) throws Exception {
		
		String message = "Hi, <b>"+savedUser.getFirstName()+" </b> "
				+ "<br> Your account is registered successfully.<br>"
				+ "<br> Click the link below to verify your account.<br>"
				+ "<a href='http://localhost:8080/api/v1/home/verify?id=" 
				+ savedUser.getId() 
				+ "&VC=" 
				+ savedUser.getStatus().getVerificationCode() 
				+ "'>Click Here</a><br><br>"
				+ "Thanks, <br> Enotes.anshuman.com"; 
		
		EmailRequest emailRequest = EmailRequest.builder()
				.to(savedUser.getEmail())
				.subject("Account Registered Successfully.")
				.title("Account Creation Confirmation.")
				.message(message)
				.build();
		emailService.send(emailRequest);
	}

	private void setRoles(UserDto userDto, User user) {
		
		List<Integer> roleIdList = userDto.getRoles().stream().map(role -> role.getId()).toList();
		List<Role> allRolesById = roleRepository.findAllById(roleIdList);
		user.setRoles(allRolesById);
	}

}
