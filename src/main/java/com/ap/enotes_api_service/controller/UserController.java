package com.ap.enotes_api_service.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ap.enotes_api_service.dto.PasswordChangeRequest;
import com.ap.enotes_api_service.dto.UserResponseDto;
import com.ap.enotes_api_service.entity.User;
import com.ap.enotes_api_service.service.UserService;
import com.ap.enotes_api_service.utils.CommonUtil;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserService userService;
	
	@GetMapping("/profile")
	public ResponseEntity<?> getProfile(){
		
		User loggedInUser = CommonUtil.getLoggedInUser();
		UserResponseDto mapedLoggedInUserResponse = modelMapper.map(loggedInUser, UserResponseDto.class);
		return CommonUtil.CreateBuildResponse(mapedLoggedInUserResponse, HttpStatus.OK);
	}
	
	@PostMapping("/change-password")
	public ResponseEntity<?> chabgePassword(@RequestBody PasswordChangeRequest passwordChangeRequest){
		
		userService.ChangePassword(passwordChangeRequest);
		return CommonUtil.CreateBuildResponseMessage("Password changed successfully!!", HttpStatus.OK);
	}
	
}
