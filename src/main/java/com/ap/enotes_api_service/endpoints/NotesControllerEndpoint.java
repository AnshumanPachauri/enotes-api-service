package com.ap.enotes_api_service.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import static com.ap.enotes_api_service.utils.Constants.ROLE_ADMIN;
import static com.ap.enotes_api_service.utils.Constants.ROLE_ADMIN_USER;
import static com.ap.enotes_api_service.utils.Constants.ROLE_USER;
import static com.ap.enotes_api_service.utils.Constants.DEFAULT_PAGE_NO;
import static com.ap.enotes_api_service.utils.Constants.DEFAULT_PAGE_SIZE;


public interface NotesControllerEndpoint {

	
	@PostMapping("/")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile multipartFile) throws Exception;
	
	@GetMapping("/")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getAllNotes();
	
	@GetMapping("/download/{id}")
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;
	
	@GetMapping("/user-notes/{userId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getAllNotesByUser(@RequestParam(name="pageNo",required = true, defaultValue = DEFAULT_PAGE_NO)  Integer pageNo,
											@RequestParam(name="pageSize",required = true, defaultValue = DEFAULT_PAGE_SIZE)  Integer pageSize);
	
	@GetMapping("/search")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> searchNotesByUser(@RequestParam(name="keyword", defaultValue = "") String keyword,
			@RequestParam(name="pageNo",required = true, defaultValue = DEFAULT_PAGE_NO)  Integer pageNo,
			@RequestParam(name="pageSize",required = true, defaultValue = DEFAULT_PAGE_SIZE)  Integer pageSize);
	
	@GetMapping("/delete/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;
	
	@GetMapping("/restore/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> restoreSoftDeletedNotes(@PathVariable Integer id) throws Exception;
	
	@GetMapping("/recycle-bin")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getUserRecycleBinNotes() throws Exception;
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;
	
	@DeleteMapping("/delete")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> emptyRecycleBin() throws Exception;
	
	@GetMapping("/fav/{noteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> favouriteNote(@PathVariable Integer noteId) throws Exception;
	
	@DeleteMapping("/un-fav/{favouriteNoteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> unFavouriteNote(@PathVariable Integer favouriteNoteId) throws Exception;
	
	@GetMapping("/fav-notes")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getserFavouriteNotes() throws Exception;
	
	@GetMapping("/copy/{noteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> copyNote(@PathVariable Integer noteId) throws Exception;
	
	
}
