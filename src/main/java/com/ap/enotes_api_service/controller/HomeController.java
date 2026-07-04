package com.ap.enotes_api_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ap.enotes_api_service.dto.PasswordResetRequestDto;
import com.ap.enotes_api_service.endpoints.HomeControllerEndpoint;
import com.ap.enotes_api_service.service.HomeService;
import com.ap.enotes_api_service.service.UserService;
import com.ap.enotes_api_service.utils.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/home")
public class HomeController implements HomeControllerEndpoint {

	@Autowired
	private HomeService homeService;
	@Autowired
	private UserService userService;
	
//	@GetMapping("/verify")
	@Override
	public ResponseEntity<?> verifyAccount(@RequestParam int id, @RequestParam String VC) throws Exception{
		log.info("--- [enotes-api-service] [Home Controller] VerifyAccount() : Execution Started.");
		Boolean verifyAccount = homeService.verifyAccount(id, VC);
		if(verifyAccount) {
			log.info("--- [enotes-api-service] [Home Controller] VerifyAccount() : Execution Completed.");
			return CommonUtil.CreateBuildResponseMessage("Account Verified", HttpStatus.OK);
		}
		return CommonUtil.CreateErrorResponseMessage("Invalid URL.", HttpStatus.BAD_REQUEST);
	}
	
//	@GetMapping("/send-email-reset")
	@Override
	public ResponseEntity<?> sendEmailForpasswordReset(@RequestParam String email, HttpServletRequest servletRequest) throws Exception{
		log.info("--- [enotes-api-service] [Home Controller] sendEmailForpasswordReset() : Execution Started.");
		userService.sendEmailPasswordReset(email, servletRequest);
		return CommonUtil.CreateBuildResponseMessage("Email sent successfully, verify the link to reset password.", HttpStatus.OK);
	}
	
//	@GetMapping("/verify-password-link")
	@Override
	public ResponseEntity<?> VerifyPasswordResetLink(@RequestParam Integer id, @RequestParam String code) throws Exception{
		userService.verifyPasswordResetLink(id, code);
		return CommonUtil.CreateBuildResponseMessage("Verification successful", HttpStatus.OK);
	}
	
//	@PostMapping("/reset-password")
	@Override
	public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequestDto passwordResetRequestDto) throws Exception{
		userService.resetPassword(passwordResetRequestDto);
		return CommonUtil.CreateBuildResponseMessage("Password reset successfully", HttpStatus.OK);
	}
	
	
}
