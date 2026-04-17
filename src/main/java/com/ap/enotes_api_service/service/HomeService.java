package com.ap.enotes_api_service.service;

public interface HomeService {
	
	public Boolean verifyAccount(Integer userId, String VerificationCode) throws Exception;
	
}
