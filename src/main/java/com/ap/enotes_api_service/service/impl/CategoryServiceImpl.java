package com.ap.enotes_api_service.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.ap.enotes_api_service.controller.CategoryController;
import com.ap.enotes_api_service.dto.CategoryDto;
import com.ap.enotes_api_service.dto.CategoryResponseDto;
import com.ap.enotes_api_service.entity.Category;
import com.ap.enotes_api_service.exception.ExistingDataException;
import com.ap.enotes_api_service.exception.ResourceNotFoundException;
import com.ap.enotes_api_service.repository.CategoryRepository;
import com.ap.enotes_api_service.service.CategoryService;
import com.ap.enotes_api_service.utils.Validation;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private Validation validation;
	
	@Override
	public Boolean saveCategory(CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		
//		Validation Checking
		
		validation.categoryValidation(categoryDto);
		
		//checking if the category already exists or not....
		
		Boolean exists = categoryRepository.existsByName(categoryDto.getName().trim());
		
		if(exists) {
				throw new ExistingDataException("Category with Name:- " + categoryDto.getName().trim() + ", already exists.");
		}
		
		/*
		 * // this will map data from categoryDto to category class entities. // For
		 * this the column names in category class must be same as the names in
		 * categoryDTO class.
		 */		
		
//		this explaination is given so that i wont get confused in mapper and stream........
		
		//This will map the data of one single categoryDto to category class one by one.
		//eg:-dategoryDto.name = category.name......etc
		
		Category category = mapper.map(categoryDto, Category.class);
		
		if(ObjectUtils.isEmpty(category.getId())) {
			category.setIsDeleted(false);
//			category.setCreatedDate(new Date(0));
		}
		else {
			updateCategory(category);
		}
		
		Category savedCaegory =  categoryRepository.save(category);
		
		if(ObjectUtils.isEmpty(savedCaegory) || savedCaegory == null) {
			return false;
		}
		
		return true;
		
	}
	
	private void updateCategory(Category category) {
		Optional<Category> findExistingById = categoryRepository.findById(category.getId());
		
		if(findExistingById.isPresent()) {
			Category existingCategory = findExistingById.get();
			
			category.setCreatedBy(existingCategory.getCreatedBy());
			category.setCreatedDate(existingCategory.getCreatedDate());
			category.setIsDeleted(existingCategory.getIsDeleted());
			
//			category.setUpdatedBy(existingCategory.getUpdatedBy());
//			category.setUpdatedDate(new Date(0));
		}
	}
	

	@Override
	public List<CategoryDto> getAllCategories() {
		// TODO Auto-generated method stub
		List<Category> categories =  categoryRepository.findAllByIsDeletedFalse();
		
		/*
		 * Here we are extracting a list of categories from database and then by using
		 * stream.map, we are taking one category at a time then the one category we get
		 * from stream.map, we are mapping its data from category to categoryDto one by
		 * one. then we take another category and do the same using mapper. Hence, the
		 * stream.map gives one category at a time from list of categories, then
		 * mapper.map maps data of each category to catedoryDto.
		 */
		
		List<CategoryDto> categoryDtoList = categories.stream().map(cat -> mapper.map(cat, CategoryDto.class)).toList();
		
		return categoryDtoList;
	}

	@Override
	public List<CategoryResponseDto> getActiveCategories() {
		// TODO Auto-generated method stub
		
		List<Category> categories = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
		
		List<CategoryResponseDto> categoryResponseDtoList = categories.stream().map(cat -> mapper.map(cat, CategoryResponseDto.class)).toList();
		return categoryResponseDtoList;
	}

	@Override
	public CategoryDto getCategoryById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		
		Category categoryById = categoryRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new ResourceNotFoundException("Category Not Found with Id = "+id));
		
		if(!ObjectUtils.isEmpty(categoryById)) {
			return mapper.map(categoryById, CategoryDto.class);
		}
		
		return null;
	}

	@Override
	public Boolean deleteCategoryById(Integer id) {
		// TODO Auto-generated method stub
		Optional<Category> categoryById = categoryRepository.findById(id);
		
		if(categoryById.isPresent()) {
			Category category = categoryById.get();
			category.setIsDeleted(true);
			categoryRepository.save(category);
			return true;
		}
		
		return false;
	}

}
