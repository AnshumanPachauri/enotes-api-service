package com.ap.enotes_api_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ap.enotes_api_service.dto.LoginRequestDto;
import com.ap.enotes_api_service.dto.LoginResponseDto;
import com.ap.enotes_api_service.dto.UserRequestDto;
import com.ap.enotes_api_service.endpoints.AuthControllerEndpoint;
import com.ap.enotes_api_service.service.AuthService;
import com.ap.enotes_api_service.utils.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthControllerEndpoint{

	@Autowired
	private AuthService authService;
	
	
	@Override
	public ResponseEntity<?> registerUser(@RequestBody UserRequestDto userDto, HttpServletRequest request) throws Exception{
		
		String url =  CommonUtil.getRequestUrl(request);
		
		Boolean register = authService.register(userDto, url);
		
		if(register) {
			return CommonUtil.CreateBuildResponseMessage("user registered successfully", HttpStatus.CREATED);
		}
		return CommonUtil.CreateErrorResponseMessage("User Not registered.", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	
	@Override
	public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) throws Exception{
		
		LoginResponseDto loginResponseDto = authService.login(loginRequestDto);
		
		if(!ObjectUtils.isEmpty(loginResponseDto)) {
			return CommonUtil.CreateBuildResponse(loginResponseDto, HttpStatus.OK);
		}
		return CommonUtil.CreateErrorResponseMessage("Invalid Credentials.", HttpStatus.UNAUTHORIZED);
		
	}
	
}
