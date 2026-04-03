package com.ap.enotes_api_service.service;

import java.util.List;

import com.ap.enotes_api_service.dto.NotesDto;

public interface NotesService {

	public Boolean saveNotes(NotesDto notesDto) throws Exception;
	
	public List<NotesDto> getAllNotes();
	
	
}
