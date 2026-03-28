package com.ap.enotes_api_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {

	private Integer id;
	
	private String name;
	
	private String description;
	
}
