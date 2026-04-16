package com.ap.enotes_api_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ap.enotes_api_service.dto.UserDto;
import com.ap.enotes_api_service.service.UserService;
import com.ap.enotes_api_service.utils.CommonUtil;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) throws Exception{
		
		Boolean register = userService.register(userDto);
		
		if(register) {
			return CommonUtil.CreateBuildResponseMessage("user registered successfully", HttpStatus.CREATED);
		}
		return CommonUtil.CreateErrorResponseMessage("User Not registered.", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
}
