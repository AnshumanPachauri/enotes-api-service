package com.ap.enotes_api_service.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoDto {
	
	private Integer id;
	
	private String title;
	
	private StatusDto status;
	
	private Integer createdBy;
	
	private Date createdDate;
	
	private Integer updatedBy;
	
	private Date updatedDate;
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class StatusDto{
		private Integer id;
		private String name;
	}
	
}
