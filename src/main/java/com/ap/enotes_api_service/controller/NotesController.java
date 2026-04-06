package com.ap.enotes_api_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ap.enotes_api_service.dto.NotesDto;
import com.ap.enotes_api_service.dto.NotesResponseDto;
import com.ap.enotes_api_service.entity.FileDetails;
import com.ap.enotes_api_service.entity.Notes;
import com.ap.enotes_api_service.exception.ResourceNotFoundException;
import com.ap.enotes_api_service.service.NotesService;
import com.ap.enotes_api_service.utils.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

	@Autowired
	private NotesService notesService;
	
	/*
	 * Changing the requestBody giving Notes from postman to controller......to
	 * requestParams, so that we can get files from request params by using
	 * multiPart and save them locally, we will not save the file in DB. we will
	 * save it locally in a folder and while deploying we will save in s3 bucket.
	 */
	
	@PostMapping("/")
	public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile multipartFile) throws Exception{
		
		/*
		 * As our saveNotes method takes notesDto as input, we will have to change its
		 * implementation so that it takes notes String and file, then convert the notes
		 * sring to notes object.
		 */
		
		Boolean saveNotes = notesService.saveNotes(notes, multipartFile);
		
		if(saveNotes) {
			return CommonUtil.CreateBuildResponseMessage("Note Saved---Ho Gaya", HttpStatus.CREATED);
		}
		
		return CommonUtil.CreateErrorResponseMessage("Note Not Saved---Nahi hua", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllNotes(){
		
		List<NotesDto> allNotes = notesService.getAllNotes();
		
		if(CollectionUtils.isEmpty(allNotes)) {
			return ResponseEntity.noContent().build();
		}
		
		return CommonUtil.CreateBuildResponse(allNotes, HttpStatus.OK);
	}
	
	@GetMapping("/download/{id}")
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception{
		
		FileDetails fileDetails = notesService.getFileDetails(id);
		
		byte[] fileData = notesService.downloadFile(fileDetails);
		
		HttpHeaders headers = new HttpHeaders();
		String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());
		return ResponseEntity.ok().headers(headers).body(fileData);
	}
	
	@GetMapping("/user-notes/{userId}")
	public ResponseEntity<?> getAllNotesByUser(@RequestParam(name="pageNo",required = true, defaultValue = "0")  Integer pageNo,
											@RequestParam(name="pageSize",required = true, defaultValue = "3")  Integer pageSize){
		
		Integer userId = 2;
		
		NotesResponseDto notes = notesService.getAllNotesByUser(userId, pageNo, pageSize);
		
		if(ObjectUtils.isEmpty(notes)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.CreateBuildResponse(notes, HttpStatus.OK);
	}
	
	
	@GetMapping("/delete/{id}")
	public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception{
		
		notesService.softDeleteNotes(id);
		
		return CommonUtil.CreateBuildResponseMessage("Note Deleted with Id = " + id, HttpStatus.OK);
		
	}
	
	@GetMapping("/restore/{id}")
	public ResponseEntity<?> restoreSoftDeletedNotes(@PathVariable Integer id) throws Exception{
		
		notesService.restoreNotes(id);
		
		return CommonUtil.CreateBuildResponseMessage("Note Restored with Id = " + id, HttpStatus.OK);
		
	}
	
	
	
}
