package com.ap.enotes_api_service.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ap.enotes_api_service.dto.NotesDto;

public interface NotesService {

	public Boolean saveNotes(String notes, MultipartFile multipartFile) throws Exception;
	
	public List<NotesDto> getAllNotes();
	
	
}
