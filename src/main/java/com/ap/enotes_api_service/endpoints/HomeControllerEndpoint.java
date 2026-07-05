package com.ap.enotes_api_service.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ap.enotes_api_service.dto.PasswordResetRequestDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name="Home", description = "Contains all the Home api's")
public interface HomeControllerEndpoint {

	
	@Operation(summary = "Verify User Account", tags= {}, description = "User can verify their account after registreation")
	@GetMapping("/verify")
	public ResponseEntity<?> verifyAccount(@RequestParam int id, @RequestParam String VC) throws Exception;
	
	
	@Operation(summary = "Send Password Reset Email", tags= {}, description = "User can send email for password reset")
	@GetMapping("/send-email-reset")
	public ResponseEntity<?> sendEmailForpasswordReset(@RequestParam String email, HttpServletRequest servletRequest) throws Exception;
	
	
	@Operation(summary = "Verify Password Reset Link", tags= {}, description = "User verification password link")
	@GetMapping("/verify-password-link")
	public ResponseEntity<?> VerifyPasswordResetLink(@RequestParam Integer id, @RequestParam String code) throws Exception;
	
	
	@Operation(summary = "Password Reset", tags= {}, description = "User can reset their password")
	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequestDto passwordResetRequestDto) throws Exception;
	
	
}
