package com.ap.enotes_api_service.service.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ap.enotes_api_service.entity.Category;
import com.ap.enotes_api_service.repository.CategoryRepository;
import com.ap.enotes_api_service.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public Boolean saveCategory(Category category) {
		// TODO Auto-generated method stub
		category.setIsDeleted(false);
		category.setCreatedDate(new Date(0));
		Category savedCaegory =  categoryRepository.save(category);
		
		if(ObjectUtils.isEmpty(savedCaegory) || savedCaegory == null) {
			return false;
		}
		
		return true;
		
	}

	@Override
	public List<Category> getAllCategories() {
		// TODO Auto-generated method stub
		List<Category> categories =  categoryRepository.findAll();
		return categories;
	}

}
