package com.ap.enotes_api_service.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ap.enotes_api_service.dto.CategoryDto;
import com.ap.enotes_api_service.dto.NotesDto;
import com.ap.enotes_api_service.dto.TodoDto;
import com.ap.enotes_api_service.dto.TodoDto.StatusDto;
import com.ap.enotes_api_service.dto.UserDto;
import com.ap.enotes_api_service.enums.TodoStatus;
import com.ap.enotes_api_service.exception.ResourceNotFoundException;
import com.ap.enotes_api_service.exception.ValidationException;
import com.ap.enotes_api_service.repository.RoleRepository;

@Component
public class Validation {

	@Autowired
	private RoleRepository roleRepository;
	
	public void categoryValidation(CategoryDto categoryDto) {
		Map<String, Object> error = new LinkedHashMap<String, Object>();
		if(ObjectUtils.isEmpty(categoryDto)) {
			throw new IllegalArgumentException("Category Object/JSON shouldn't be empty or null");
		}
		else {
			
			//Validation for Name Field.
			if(ObjectUtils.isEmpty(categoryDto.getName())) {
				error.put("Name", "Name Field cannot be empty or null");
			}else {
				if(categoryDto.getName().length() < 3) {
					error.put("Name", "Name length is minimum 3");
				}
				if(categoryDto.getName().length() > 100) {
					error.put("Name", "Name length is maximum 100");
				}
			}
			
			//Validation for Description Field.
			if(ObjectUtils.isEmpty(categoryDto.getDescription())) {
				error.put("Description", "Description Field cannot be empty or null");
			}else {
				if(categoryDto.getDescription().length() < 10) {
					error.put("Description", "Description length is minimum 10");
				}
				if(categoryDto.getDescription().length() > 1000) {
					error.put("Description", "Description length is maximum 1000");
				}
			}

			//Validation for IsActive Field.
			if(ObjectUtils.isEmpty(categoryDto.getIsActive())) {
				error.put("IsActive", "IsActive Field cannot be empty or null");
			}else {
				if(categoryDto.getIsActive() != Boolean.TRUE && categoryDto.getIsActive() != Boolean.FALSE) {
					error.put("IsActive", "Invalid Value of IsActive Field");
				}
			}
			
		}
		
		if(!error.isEmpty()) {
			throw new ValidationException(error);
		}
		
	}
	
	
	public void NotesValidation(NotesDto notesDto) {
		Map<String, Object> error = new LinkedHashMap<String, Object>();
		if(ObjectUtils.isEmpty(notesDto)) {
			throw new IllegalArgumentException("Notes Object/JSON shouldn't be empty or null");
		}
		else {
			
			//Validation for Name Field.
			if(ObjectUtils.isEmpty(notesDto.getTitle())) {
				error.put("Title", "Title Field cannot be empty or null");
			}else {
				if(notesDto.getTitle().length() < 3) {
					error.put("Title", "Title length is minimum 3");
				}
				if(notesDto.getTitle().length() > 100) {
					error.put("Title", "Title length is maximum 100");
				}
			}
			
			//Validation for Description Field.
			if(ObjectUtils.isEmpty(notesDto.getDescription())) {
				error.put("Description", "Description Field cannot be empty or null");
			}else {
				if(notesDto.getDescription().length() < 10) {
					error.put("Description", "Description length is minimum 10");
				}
				if(notesDto.getDescription().length() > 10000) {
					error.put("Description", "Description length is maximum 10000");
				}
			}
			
		}
		
		if(!error.isEmpty()) {
			throw new ValidationException(error);
		}
		
	}
	
	public void todoValidation(TodoDto todoDto) throws Exception {
		
		TodoStatus[] status = TodoStatus.values();
		StatusDto todoDtoStatus = todoDto.getStatus();
		Boolean statusFound = false;
		
		for(TodoStatus st : status) {
			if(todoDtoStatus.getId().equals(st.getId())) {
				statusFound = true;
			}
		}
		
		if(!statusFound) {
			throw new ResourceNotFoundException("Invalid Status");
		}
		
	}
	
	public void userValidation(UserDto userDto) {
		
		if(!StringUtils.hasText(userDto.getFirstName())) {
			throw new IllegalArgumentException("First Name is Invalid");
		}
		
		if(!StringUtils.hasText(userDto.getLastName())) {
			throw new IllegalArgumentException("Last Name is Invalid");
		}
		
		if(!StringUtils.hasText(userDto.getEmail()) || !userDto.getEmail().matches(Constants.EMAIL_REGEX)) {
			throw new IllegalArgumentException("Email is Invalid");
		}
		
		if(!StringUtils.hasText(userDto.getMobileNumber()) || !userDto.getMobileNumber().matches(Constants.MOBILE_NUMBER_REGEX)) {
			throw new IllegalArgumentException("Mobile Number is Invalid");
		}
		
		if(CollectionUtils.isEmpty(userDto.getRoles())) {
			throw new IllegalArgumentException("Role Not given");
		}
		else {
			List<Integer> rollIds = roleRepository.findAll().stream().map(r -> r.getId()).toList();
			
			List<Integer> invalidReqRollid = userDto.getRoles().stream().map(r -> r.getId()).filter(rollId -> !rollIds.contains(rollIds)).toList();
			if(CollectionUtils.isEmpty(invalidReqRollid)){
				throw new IllegalArgumentException("Role is Invalid" + invalidReqRollid);
			}
		
		}
		
	}
	
}
