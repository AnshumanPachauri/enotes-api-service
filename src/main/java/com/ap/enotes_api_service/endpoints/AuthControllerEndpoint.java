package com.ap.enotes_api_service.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ap.enotes_api_service.dto.LoginRequestDto;
import com.ap.enotes_api_service.dto.UserRequestDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name="Authentication", description = "Contains all the user authentication api's")
public interface AuthControllerEndpoint {

	@Operation(summary = "User Registeration Endpoint", tags= {"Authentication", "Home"})
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserRequestDto userDto, HttpServletRequest request) throws Exception;
	
	@Operation(summary = "User Login Endpoint", tags= {"Authentication", "Home"})
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) throws Exception;
	
	
	
}
