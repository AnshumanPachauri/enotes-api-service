package com.ap.enotes_api_service.controller;
import com.ap.enotes_api_service.service.impl.CategoryServiceImpl;

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

import com.ap.enotes_api_service.dto.CategoryDto;
import com.ap.enotes_api_service.dto.CategoryResponseDto;
import com.ap.enotes_api_service.entity.Category;
import com.ap.enotes_api_service.service.CategoryService;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/save-category")
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto) {
		
		Boolean saveCatedory = categoryService.saveCategory(categoryDto);
		
		if(saveCatedory) {
			return new ResponseEntity<>("Saved---Ho Gya", HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>("Not Saved---Ni Hua", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/category")
	public ResponseEntity<?> getAllCategory(){
		
		List<CategoryDto> allCategories = categoryService.getAllCategories();
		
		if(CollectionUtils.isEmpty(allCategories)) {
			return ResponseEntity.noContent().build();
		}
		else {
			return new ResponseEntity<>(allCategories, HttpStatus.OK);
		}
		
	}
	
	@GetMapping("/active-category")
	public ResponseEntity<?> getActiveCategory(){
		
		List<CategoryResponseDto> activeCategories = categoryService.getActiveCategories();
		
		if(CollectionUtils.isEmpty(activeCategories)) {
			return ResponseEntity.noContent().build();
		}
		else {
			return new ResponseEntity<>(activeCategories, HttpStatus.OK);
		}
		
	}
	
}
