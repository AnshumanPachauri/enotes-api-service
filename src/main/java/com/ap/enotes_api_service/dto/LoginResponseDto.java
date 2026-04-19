package com.ap.enotes_api_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {

	private UserDto userDto;
	
	private String token;
	
}
