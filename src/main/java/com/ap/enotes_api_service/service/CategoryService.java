package com.ap.enotes_api_service.service;

import java.util.List;

import com.ap.enotes_api_service.entity.Category;

public interface CategoryService {
	
	public Boolean saveCategory(Category category);
	
	public List<Category> getAllCategories();
	
}
