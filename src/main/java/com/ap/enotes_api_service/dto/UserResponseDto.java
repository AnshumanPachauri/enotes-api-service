package com.ap.enotes_api_service.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {


	private Integer id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
//	private String password;
	
	private String mobileNumber;
	
	private StatusDto status;

	private List<RoleDto> roles;
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class RoleDto{
		
		private Integer id;
		private String name;
		
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class StatusDto{
		
		private int id;
		private Boolean isActive;
		
	}
	
}
