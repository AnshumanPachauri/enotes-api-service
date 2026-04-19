package com.ap.enotes_api_service.service;

import com.ap.enotes_api_service.dto.LoginRequestDto;
import com.ap.enotes_api_service.dto.LoginResponseDto;
import com.ap.enotes_api_service.dto.UserDto;

public interface UserService {

	public Boolean register(UserDto userDto, String url) throws Exception;

	public LoginResponseDto login(LoginRequestDto loginRequestDto);
	
}
