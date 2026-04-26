package com.ap.enotes_api_service.service;

import com.ap.enotes_api_service.dto.LoginRequestDto;
import com.ap.enotes_api_service.dto.LoginResponseDto;
import com.ap.enotes_api_service.dto.UserRequestDto;

public interface AuthService {

	public Boolean register(UserRequestDto userDto, String url) throws Exception;

	public LoginResponseDto login(LoginRequestDto loginRequestDto);
	
}
