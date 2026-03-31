package com.ap.enotes_api_service.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.ap.enotes_api_service.dto.CategoryDto;
import com.ap.enotes_api_service.exception.ValidationException;

@Component
public class Validation {

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
				if(categoryDto.getName().length() < 10) {
					error.put("Name", "Name length is minimum 10");
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
				if(categoryDto.getDescription().length() > 100) {
					error.put("Description", "Description length is maximum 100");
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
	
}
