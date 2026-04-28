package com.ap.enotes_api_service.service.impl;

import java.util.List;
import java.util.UUID;

import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ap.enotes_api_service.config.security.CustomUserDetails;
import com.ap.enotes_api_service.dto.EmailRequest;
import com.ap.enotes_api_service.dto.LoginRequestDto;
import com.ap.enotes_api_service.dto.LoginResponseDto;
import com.ap.enotes_api_service.dto.UserRequestDto;
import com.ap.enotes_api_service.dto.UserResponseDto;
import com.ap.enotes_api_service.entity.AccountStatus;
import com.ap.enotes_api_service.entity.Role;
import com.ap.enotes_api_service.entity.User;
import com.ap.enotes_api_service.repository.RoleRepository;
import com.ap.enotes_api_service.repository.UserRepository;
import com.ap.enotes_api_service.service.JWTService;
import com.ap.enotes_api_service.service.AuthService;
import com.ap.enotes_api_service.utils.Validation;


@Service
public class AuthServiceImpl implements AuthService {

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
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired 
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private JWTService jwtService;
	
	@Override
	public Boolean register(UserRequestDto userDto, String url) throws Exception {
		
		validation.userValidation(userDto);
		
		User mappedUser = modelMapper.map(userDto, User.class);
		setRoles(userDto, mappedUser);
		
		AccountStatus status = AccountStatus.builder()
				.isActive(false)
				.verificationCode(UUID.randomUUID().toString())
				.build();
		mappedUser.setStatus(status);
		mappedUser.setPassword(bCryptPasswordEncoder.encode(mappedUser.getPassword()));
		User savedUser = userRepository.save(mappedUser);
		
		if(ObjectUtils.isEmpty(savedUser)) {
			return false;
		}
		sendEmailForUserRegister(savedUser, url);
		return true;
	}

	private void sendEmailForUserRegister(User savedUser, String url) throws Exception {
		
		String message = "Hi, <b>"+savedUser.getFirstName()+" </b> "
				+ "<br> Your account is registered successfully.<br>"
				+ "<br> Click the link below to verify your account.<br>"
				+ "<a href='"+url+"/api/v1/home/verify?id=" 
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

	private void setRoles(UserRequestDto userDto, User user) {
		
		List<Integer> roleIdList = userDto.getRoles().stream().map(role -> role.getId()).toList();
		List<Role> allRolesById = roleRepository.findAllById(roleIdList);
		user.setRoles(allRolesById);
	}

	@Override
	public LoginResponseDto login(LoginRequestDto loginRequestDto) {
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
		
		if(authenticate.isAuthenticated()) {
			
			@Nullable
			CustomUserDetails customUserDetails = (CustomUserDetails) authenticate.getPrincipal();
			
			//Token contains of 3 parts,
			//Header-----Payload-----signature
			
			String token = jwtService.generateToken(customUserDetails.getUser());
			
			LoginResponseDto loginResponseDto = LoginResponseDto.builder()
					.userDto(modelMapper.map(customUserDetails.getUser(), UserResponseDto.class))
					.token(token)
					.build();
			return loginResponseDto;
		}
		
		return null;
	}

}
