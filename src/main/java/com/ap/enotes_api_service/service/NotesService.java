package com.ap.enotes_api_service.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ap.enotes_api_service.dto.NotesDto;
import com.ap.enotes_api_service.dto.NotesResponseDto;
import com.ap.enotes_api_service.entity.FileDetails;

public interface NotesService {

	public Boolean saveNotes(String notes, MultipartFile multipartFile) throws Exception;
	
	public List<NotesDto> getAllNotes();

	public byte[] downloadFile(FileDetails fileDetails) throws Exception;

	public FileDetails getFileDetails(Integer id) throws Exception;

	public NotesResponseDto getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize);

	public void softDeleteNotes(Integer id) throws Exception;

	public void restoreNotes(Integer id) throws Exception;
	
	
}
