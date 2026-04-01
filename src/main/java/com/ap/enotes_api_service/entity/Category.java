package com.ap.enotes_api_service.entity;



import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Category extends CommonBaseModel{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	private String description;
	
	private Boolean isActive;
	
	private Boolean isDeleted;
}

/*
 * @EntityListeners(AuditingEntityListener.class) this will enable this category
 * entity class to be able for auditing. so, whenever this class is changed, it
 * will be audited by EntityListener.
 */
