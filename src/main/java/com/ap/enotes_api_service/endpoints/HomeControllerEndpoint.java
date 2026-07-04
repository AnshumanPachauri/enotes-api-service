package com.ap.enotes_api_service.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ap.enotes_api_service.dto.PasswordResetRequestDto;

import jakarta.servlet.http.HttpServletRequest;

public interface HomeControllerEndpoint {

	@GetMapping("/verify")
	public ResponseEntity<?> verifyAccount(@RequestParam int id, @RequestParam String VC) throws Exception;
	
	@GetMapping("/send-email-reset")
	public ResponseEntity<?> sendEmailForpasswordReset(@RequestParam String email, HttpServletRequest servletRequest) throws Exception;
	
	@GetMapping("/verify-password-link")
	public ResponseEntity<?> VerifyPasswordResetLink(@RequestParam Integer id, @RequestParam String code) throws Exception;
	
	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequestDto passwordResetRequestDto) throws Exception;
	
	
}
