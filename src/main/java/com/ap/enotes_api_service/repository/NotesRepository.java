package com.ap.enotes_api_service.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ap.enotes_api_service.entity.Notes;

public interface NotesRepository extends JpaRepository<Notes, Integer>{
	Page<Notes> findAllByCreatedByAndIsDeletedFalse(Integer id, Pageable pagable);

	List<Notes> findByCreatedByAndIsDeletedTrue(Integer userId);

	List<Notes> findAllByIsDeletedAndDeletedOnBefore(boolean b, LocalDateTime minusDays);
	
	@Query("select n from Notes n where lower(n.title) like lower(concat('%',:keyword,'%'))"
								  + "or lower(n.description) like lower(concat('%',:keyword,'%'))"
								  + "or lower(n.category.name) like lower(concat('%',:keyword,'%'))"
								  + "and isDeleted=false"
								  + "and createdBy=:userId")
	Page<Notes> searchNotes(String keyword, Integer userId, Pageable pagable);

}
