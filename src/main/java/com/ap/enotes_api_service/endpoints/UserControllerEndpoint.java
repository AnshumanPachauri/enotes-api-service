package com.ap.enotes_api_service.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ap.enotes_api_service.dto.PasswordChangeRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="User", description = "Contains all the Authenticated User Operation api's")
public interface UserControllerEndpoint {

	
	@Operation(summary = "Get User Profile", tags= {}, description = "Gets the profile of logged-in user")
	@GetMapping("/profile")
	public ResponseEntity<?> getProfile();
	
	
	@Operation(summary = "Change User Password", tags= {}, description = "Logged-In user can change their password")
	@PostMapping("/change-password")
	public ResponseEntity<?> chabgePassword(@RequestBody PasswordChangeRequest passwordChangeRequest);
	
}
