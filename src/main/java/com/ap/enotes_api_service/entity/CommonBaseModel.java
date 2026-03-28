package com.ap.enotes_api_service.entity;

import java.sql.Date;

import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
@Data
@MappedSuperclass
public class CommonBaseModel {
	
	private Boolean isActive;
	
	private Boolean isDeleted;
	
	private Integer createdBy;
	
	
	
	private Date createdDate;
	
	private Integer updatedBy;
	
	private Date updatedDate;
}
