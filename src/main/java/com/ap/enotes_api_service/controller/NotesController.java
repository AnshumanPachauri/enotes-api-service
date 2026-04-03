package com.ap.enotes_api_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ap.enotes_api_service.dto.NotesDto;
import com.ap.enotes_api_service.service.NotesService;
import com.ap.enotes_api_service.utils.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

	@Autowired
	private NotesService notesService;
	
	@PostMapping("/")
	public ResponseEntity<?> saveNotes(@RequestBody NotesDto notesDto) throws Exception{
		
		Boolean saveNotes = notesService.saveNotes(notesDto);
		
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
	
	
}
