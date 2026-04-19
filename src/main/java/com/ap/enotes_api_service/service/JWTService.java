package com.ap.enotes_api_service.service;

import com.ap.enotes_api_service.entity.User;

public interface JWTService {

	public String generateToken(User user);
	
}
