package com.ap.enotes_api_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ap.enotes_api_service.entity.FileDetails;

public interface FileRepository extends JpaRepository<FileDetails, Integer>{

}
