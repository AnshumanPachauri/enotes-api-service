package com.ap.enotes_api_service.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ap.enotes_api_service.dto.LoginRequestDto;
import com.ap.enotes_api_service.dto.UserRequestDto;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthControllerEndpoint {

	
	@PostMapping("/")
	public ResponseEntity<?> registerUser(@RequestBody UserRequestDto userDto, HttpServletRequest request) throws Exception;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) throws Exception;
	
	
	
}
