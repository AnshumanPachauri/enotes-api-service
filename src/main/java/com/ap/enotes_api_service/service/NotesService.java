package com.ap.enotes_api_service.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ap.enotes_api_service.dto.NotesDto;
import com.ap.enotes_api_service.entity.FileDetails;
import com.ap.enotes_api_service.exception.ResourceNotFoundException;

public interface NotesService {

	public Boolean saveNotes(String notes, MultipartFile multipartFile) throws Exception;
	
	public List<NotesDto> getAllNotes();

	public byte[] downloadFile(FileDetails fileDetails) throws Exception;

	public FileDetails getFileDetails(Integer id) throws Exception;
	
	
}
