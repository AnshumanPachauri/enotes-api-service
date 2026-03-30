package com.ap.enotes_api_service.service;

import java.util.List;

import com.ap.enotes_api_service.dto.CategoryDto;
//import com.ap.enotes_api_service.entity.Category;
import com.ap.enotes_api_service.dto.CategoryResponseDto;

public interface CategoryService {
	
	public Boolean saveCategory(CategoryDto categoryDto);
	
	public List<CategoryDto> getAllCategories();

	public List<CategoryResponseDto> getActiveCategories();

	public CategoryDto getCategoryById(Integer id) throws Exception;

	public Boolean deleteCategoryById(Integer id);
	
}
