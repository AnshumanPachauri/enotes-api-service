package com.ap.enotes_api_service.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ap.enotes_api_service.entity.Notes;

public interface NotesRepository extends JpaRepository<Notes, Integer>{
	Page<Notes> findAllByCreatedBy(Integer id, Pageable pagable);
}
