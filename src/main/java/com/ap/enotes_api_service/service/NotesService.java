package com.ap.enotes_api_service.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ap.enotes_api_service.dto.FavouriteNotesDto;
import com.ap.enotes_api_service.dto.NotesDto;
import com.ap.enotes_api_service.dto.NotesResponseDto;
import com.ap.enotes_api_service.entity.FavouriteNotes;
import com.ap.enotes_api_service.entity.FileDetails;

public interface NotesService {

	public Boolean saveNotes(String notes, MultipartFile multipartFile) throws Exception;
	
	public List<NotesDto> getAllNotes();

	public byte[] downloadFile(FileDetails fileDetails) throws Exception;

	public FileDetails getFileDetails(Integer id) throws Exception;

	public NotesResponseDto getAllNotesByUser(Integer pageNo, Integer pageSize);
	
	public NotesResponseDto getNotesByUserSearch(Integer pageNo, Integer pageSize, String keyword);

	public void softDeleteNotes(Integer id) throws Exception;

	public void restoreNotes(Integer id) throws Exception;

	public List<NotesDto> getUserRecycleBinNotes();

	public void hardDeleteNotes(Integer id) throws Exception;

	public void emptyRecycleBin() throws Exception;
	
	public void favouriteNotes(Integer noteId) throws Exception;
	
	public void unFavouriteNotes(Integer noteId) throws Exception;
	
	public List<FavouriteNotesDto> getUserFavouriteNotes();

	public Boolean copyNotes(Integer noteId) throws Exception;
	
}
