package com.ap.enotes_api_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ap.enotes_api_service.entity.FavouriteNotes;

public interface FavouriteNotesRepository extends JpaRepository<FavouriteNotes, Integer>{

	List<FavouriteNotes> findByUserId(int userId);

}
