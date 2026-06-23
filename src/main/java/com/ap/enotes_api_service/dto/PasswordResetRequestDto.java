package com.ap.enotes_api_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetRequestDto {

	private Integer id;
	
	private String newPassword;
	
}
