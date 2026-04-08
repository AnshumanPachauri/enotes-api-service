package com.ap.enotes_api_service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavouriteNotesDto {

	private Integer id;

	private NotesDto notes;
	
	private Integer userId;
	
}
