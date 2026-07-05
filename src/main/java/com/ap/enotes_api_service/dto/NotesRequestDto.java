package com.ap.enotes_api_service.dto;

import com.ap.enotes_api_service.dto.NotesDto.CategoryDto;
import com.ap.enotes_api_service.dto.NotesDto.FilesDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotesRequestDto {

	private String title;
	
	private String description;
	
	private CategoryDto category;
	
	
}
