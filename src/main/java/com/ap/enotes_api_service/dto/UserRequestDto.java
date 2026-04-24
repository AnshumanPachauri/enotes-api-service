package com.ap.enotes_api_service.dto;

import java.util.List;

import com.ap.enotes_api_service.entity.Role;
import com.ap.enotes_api_service.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {

	private Integer id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String password;
	
	private String mobileNumber;

	private List<RoleDto> roles;
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class RoleDto{
		
		private Integer id;
		private String name;
		
	}
	
}
