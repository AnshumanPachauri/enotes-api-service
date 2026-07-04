package com.ap.enotes_api_service.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ap.enotes_api_service.dto.PasswordChangeRequest;

public interface UserControllerEndpoint {

	
	@GetMapping("/profile")
	public ResponseEntity<?> getProfile();
	
	@PostMapping("/change-password")
	public ResponseEntity<?> chabgePassword(@RequestBody PasswordChangeRequest passwordChangeRequest);
	
}
