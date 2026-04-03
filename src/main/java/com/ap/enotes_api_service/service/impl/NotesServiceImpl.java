package com.ap.enotes_api_service.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ap.enotes_api_service.dto.NotesDto;
import com.ap.enotes_api_service.dto.NotesDto.CategoryDto;
import com.ap.enotes_api_service.entity.Category;
import com.ap.enotes_api_service.entity.Notes;
import com.ap.enotes_api_service.exception.ResourceNotFoundException;
import com.ap.enotes_api_service.repository.CategoryRepository;
import com.ap.enotes_api_service.repository.NotesRepository;
import com.ap.enotes_api_service.service.NotesService;
import com.ap.enotes_api_service.utils.Validation;

@Service
public class NotesServiceImpl implements NotesService {

	@Autowired
	private NotesRepository notesRepository;
	@Autowired
	private 	ModelMapper modelMapper;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private Validation validation;
	
	@Override
	public Boolean saveNotes(NotesDto notesDto) throws Exception {
		// TODO Auto-generated method stub
		
		
		
		//Notes Validation
		
		validation.NotesValidation(notesDto);
		
		/*
		 * //Notes Validation.....validating the category coming in notes, we validate
		 * if the Category with given Id does not exist.
		 */		
		
//		Integer CategoryId = notesDto.getCategory().getId();
		
		checkExistingCategory(notesDto.getCategory());
		
		Notes notes = modelMapper.map(notesDto, Notes.class);
		
		Notes savedNote =  notesRepository.save(notes);
		
		if(!ObjectUtils.isEmpty(savedNote)) {
			return true;
		}
		
		return false;
	}

	/*
	 * //this function checks if the category id passed in a note is valid or
	 * not....if the category with the id exists or not...
	 */	
	
	private void checkExistingCategory(CategoryDto category) throws ResourceNotFoundException {
		
		categoryRepository.findById(category.getId()).orElseThrow(() -> new ResourceNotFoundException("Invalid category ID = " + category.getId()));
		
	}

	@Override
	public List<NotesDto> getAllNotes() {
		// TODO Auto-generated method stub
		
		/*
		 * Here we are extracting a list of Notes from database and then by using
		 * stream.map, we are taking one Note at a time then the one Note we get
		 * from stream.map, we are mapping its data from Note to NotesDto one by
		 * one. then we take another Note and do the same using mapper. Hence, the
		 * stream.map gives one Note at a time from list of Notes, then
		 * mapper.map maps data of each Note to NotesDto.
		 */
		
		return notesRepository.findAll().stream().map(note -> modelMapper.map(note, NotesDto.class)).toList();

	}

}
