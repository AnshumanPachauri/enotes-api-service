package com.ap.enotes_api_service.service;

import com.ap.enotes_api_service.dto.PasswordChangeRequest;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

	public void ChangePassword(PasswordChangeRequest passwordChangeRequest);

	public void sendEmailPasswordReset(String email, HttpServletRequest httpServletRequest) throws Exception;

	public void verifyPasswordResetLink(Integer userId, String code) throws Exception;
	
}
