package com.ap.enotes_api_service.dto;

import java.util.Date;

import com.ap.enotes_api_service.entity.Category;
import com.ap.enotes_api_service.entity.Notes;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotesDto {

	private Integer id;
	
	private String title;
	
	private String description;
	
	private CategoryDto category;
	
//	private Integer userId;
	
	private Integer createdBy;
	
	private Date createdDate;
	
	private Integer updatedBy;
	
	private Date updatedDate;
	
	private FilesDto fileDetails;
	
	private Boolean isDeleted;
	
	private Date deletedOn;
	
	/*
	 * Here we don't need everything from category class, as the above categoryDTO class
	 * is providing all the table columns data from category, but we only need Id
	 * and description.....therefore we will be creating an inner class. The inner
	 * class will only give the data that we will keep in it, and will discard rest
	 * of the data coming from category entity.
	 */
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CategoryDto{
		
		private Integer id;
		private String name;
		
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class FilesDto{
		
		private Integer id;
		private String originalFileName;
		private String displayFileName;
		
	}
	
}
