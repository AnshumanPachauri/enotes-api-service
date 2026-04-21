package com.ap.enotes_api_service.service;

import org.springframework.security.core.userdetails.UserDetails;
import com.ap.enotes_api_service.entity.User;

public interface JWTService {

	public String generateToken(User user);
	
	public String extractUsername(String token);
	
	public Boolean validateToken(String token, UserDetails customUserDetails);
	
}
