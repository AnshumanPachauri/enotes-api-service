package com.ap.enotes_api_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ap.enotes_api_service.dto.CategoryDto;
import com.ap.enotes_api_service.dto.CategoryResponseDto;
import com.ap.enotes_api_service.entity.Category;
import com.ap.enotes_api_service.exception.ResourceNotFoundException;
import com.ap.enotes_api_service.service.CategoryService;
import com.ap.enotes_api_service.utils.CommonUtil;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/save")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> saveCategory(@Valid @RequestBody CategoryDto categoryDto) {
		
		Boolean saveCatedory = categoryService.saveCategory(categoryDto);
		
		if(saveCatedory) {
			return CommonUtil.CreateBuildResponseMessage("Saved---Ho Gya", HttpStatus.CREATED);
//			return new ResponseEntity<>("Saved---Ho Gya", HttpStatus.CREATED);
		}
		else {
			return CommonUtil.CreateErrorResponseMessage("Not Saved---Ni Hua", HttpStatus.INTERNAL_SERVER_ERROR);
//			return new ResponseEntity<>("Not Saved---Ni Hua", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllCategory(){
		List<CategoryDto> allCategories = categoryService.getAllCategories();
		
		if(CollectionUtils.isEmpty(allCategories)) {
			return ResponseEntity.noContent().build();
		}
		else {
			return CommonUtil.CreateBuildResponse(allCategories, HttpStatus.OK);
//			return new ResponseEntity<>(allCategories, HttpStatus.OK);
		}
		
	}
	
	@GetMapping("/active")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public ResponseEntity<?> getActiveCategory(){
		
		List<CategoryResponseDto> activeCategories = categoryService.getActiveCategories();
		
		if(CollectionUtils.isEmpty(activeCategories)) {
			return ResponseEntity.noContent().build();
		}
		else {
			
//			return new ResponseEntity<>(activeCategories, HttpStatus.OK);
			return CommonUtil.CreateBuildResponse(activeCategories, HttpStatus.OK);
		}
		
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws Exception{
		
		CategoryDto categoryDto;
		categoryDto = categoryService.getCategoryById(id);
		
		if(ObjectUtils.isEmpty(categoryDto)) {
//			return new ResponseEntity<>("Internal Server Error", HttpStatus.NOT_FOUND);
			return CommonUtil.CreateErrorResponseMessage("Internal Server Error", HttpStatus.NOT_FOUND);
		}
//		return new ResponseEntity<>(categoryDto, HttpStatus.OK);
		return CommonUtil.CreateBuildResponse(categoryDto, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteCategoryDetailsById(@PathVariable Integer id){
		
		Boolean deleted = categoryService.deleteCategoryById(id);
		
		if(deleted) {
//			return new ResponseEntity<>("Category Deleted With Id="+id, HttpStatus.OK);
			return CommonUtil.CreateBuildResponseMessage("Category Deleted With Id="+id, HttpStatus.OK);
		}
		
//		return new ResponseEntity<>("Category Not Deleted with Id="+id, HttpStatus.INTERNAL_SERVER_ERROR);
		return CommonUtil.CreateErrorResponseMessage("Category Not Deleted with Id="+id, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
}
