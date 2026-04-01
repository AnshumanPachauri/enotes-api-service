package com.ap.enotes_api_service.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class CommonBaseModel {
	//this class is made an abstract class so that an object of this class can not be created.
	
	@CreatedBy
	@Column(updatable = false)
	private Integer createdBy;
	
	@CreatedDate
	@Column(updatable = false)
	private Date createdDate;
	
	@LastModifiedBy
	@Column(insertable = false)
	private Integer updatedBy;
	
	@LastModifiedDate
	@Column(insertable = false)
	private Date updatedDate;
}

/*
 * Basically we are creating an auditor that will track this data, which will
 * help us in auditing who edited the class that is extending this base model
 * class. hence, jo bhi Category class ko edit kryga uska data common base
 * modell class m automatically audit hoga.
 */