package com.ap.enotes_api_service.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.ap.enotes_api_service.controller.CategoryController;
import com.ap.enotes_api_service.entity.Notes;
import com.ap.enotes_api_service.repository.NotesRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class NotesScheduler {
	
    private final CategoryController categoryController;
	
	@Autowired
	private NotesRepository notesRepository;

    NotesScheduler(CategoryController categoryController) {
        this.categoryController = categoryController;
    }
	
//	@Scheduled(cron = "0 0 0 * * *")
	@Scheduled(cron = "* * * * * *")
	public void deleteNotesScheduler() {
		
		LocalDateTime minusDays = LocalDateTime.now().minusDays(7);
		log.info("NotesScheduler :: DeleteNotesScheduler :: Getting the Notes 7 Days Before Today");
		List<Notes> deletedNotes = notesRepository.findAllByIsDeletedAndDeletedOnBefore(true, minusDays);
		log.info("NotesScheduler :: DeleteNotesScheduler :: Deleting the Notes");
		notesRepository.deleteAll(deletedNotes);
		log.info("NotesScheduler :: DeleteNotesScheduler :: Deleted the Notes");
	}
	
}
