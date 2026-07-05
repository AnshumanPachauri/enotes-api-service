package com.ap.enotes_api_service.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ap.enotes_api_service.dto.NotesDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import static com.ap.enotes_api_service.utils.Constants.ROLE_ADMIN;
import static com.ap.enotes_api_service.utils.Constants.ROLE_ADMIN_USER;
import static com.ap.enotes_api_service.utils.Constants.ROLE_USER;
import static com.ap.enotes_api_service.utils.Constants.DEFAULT_PAGE_NO;
import static com.ap.enotes_api_service.utils.Constants.DEFAULT_PAGE_SIZE;

@Tag(name="Notes", description = "Contains all the Notes Operation api's")
public interface NotesControllerEndpoint {

	
	@Operation(summary = "Save Notes", tags= {"User", "Notes"}, description = "User can save notes")
	@PostMapping(value = "/", consumes = "multipart/form-data")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> saveNotes(@RequestParam @Parameter(description = "Json String Notes", required = true, content = @Content(schema = @Schema(implementation = NotesDto.class))) String notes, @RequestParam(required = false) MultipartFile multipartFile) throws Exception;
	
	
	@Operation(summary = "Get All Notes", tags= {}, description = "Admin can get all notes of all users")
	@GetMapping("/")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getAllNotes();
	
	
	@Operation(summary = "Download Notes File", tags= {"User", "Notes"}, description = "User and Admin can download the file uploaded in notes")
	@GetMapping("/download/{id}")
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;
	
	
	@Operation(summary = "Get User Notes", tags= {"User", "Notes"}, description = "User can get all their notes")
	@GetMapping("/user-notes/{userId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getAllNotesByUser(@RequestParam(name="pageNo",required = true, defaultValue = DEFAULT_PAGE_NO)  Integer pageNo,
											@RequestParam(name="pageSize",required = true, defaultValue = DEFAULT_PAGE_SIZE)  Integer pageSize);
	
	
	@Operation(summary = "Search User Notes", tags= {"User", "Notes"}, description = "User Can search all their notes based on keywords")
	@GetMapping("/search")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> searchNotesByUser(@RequestParam(name="keyword", defaultValue = "") String keyword,
			@RequestParam(name="pageNo",required = true, defaultValue = DEFAULT_PAGE_NO)  Integer pageNo,
			@RequestParam(name="pageSize",required = true, defaultValue = DEFAULT_PAGE_SIZE)  Integer pageSize);
	
	
	@Operation(summary = "Soft Delete User Notes", tags= {"User", "Notes"}, description = "User can soft delete their notes")
	@GetMapping("/delete/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;
	
	
	@Operation(summary = "Restore Soft Deleted User Notes", tags= {"User", "Notes"}, description = "User can restore their soft deleted notes")
	@GetMapping("/restore/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> restoreSoftDeletedNotes(@PathVariable Integer id) throws Exception;
	
	
	@Operation(summary = "Get Soft Deleted User Notes", tags= {"User", "Notes"}, description = "User can get their soft deleted notes")
	@GetMapping("/recycle-bin")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getUserRecycleBinNotes() throws Exception;
	
	
	@Operation(summary = "Permanently Delete User Notes", tags= {"User", "Notes"}, description = "User can permanently delete their notes")
	@DeleteMapping("/delete/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;
	
	
	@Operation(summary = "Empty User Recycle Bin", tags= {"User", "Notes"}, description = "User can Empty their notes recycle bin")
	@DeleteMapping("/delete")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> emptyRecycleBin() throws Exception;
	
	
	@Operation(summary = "Make User Notes Favourite", tags= {"User", "Notes"}, description = "User can mark their notes as favourite")
	@GetMapping("/fav/{noteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> favouriteNote(@PathVariable Integer noteId) throws Exception;
	
	
	@Operation(summary = "Make User Notes UnFavourite", tags= {"User", "Notes"}, description = "User can mark their notes as unfavourite")
	@DeleteMapping("/un-fav/{favouriteNoteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> unFavouriteNote(@PathVariable Integer favouriteNoteId) throws Exception;
	
	
	@Operation(summary = "Get User Favourite Notes", tags= {"User", "Notes"}, description = "User can mark their favourite notes")
	@GetMapping("/fav-notes")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getserFavouriteNotes() throws Exception;
	
	@Operation(summary = "Copy User Notes", tags= {"User", "Notes"}, description = "User can make copy of their notes")
	@GetMapping("/copy/{noteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> copyNote(@PathVariable Integer noteId) throws Exception;
	
	
}
