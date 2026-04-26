package com.ap.enotes_api_service.service;

import com.ap.enotes_api_service.dto.PasswordChangeRequest;

public interface UserService {

	public void ChangePassword(PasswordChangeRequest passwordChangeRequest);
	
}
