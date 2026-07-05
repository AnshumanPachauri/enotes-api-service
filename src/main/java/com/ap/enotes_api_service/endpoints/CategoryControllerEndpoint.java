package com.ap.enotes_api_service.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ap.enotes_api_service.dto.CategoryDto;
import com.ap.enotes_api_service.utils.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static com.ap.enotes_api_service.utils.Constants.ROLE_ADMIN;
import static com.ap.enotes_api_service.utils.Constants.ROLE_ADMIN_USER;

import jakarta.validation.Valid;

@Tag(name="Category", description = "Contains all the notes category api's")
public interface CategoryControllerEndpoint {

	@Operation(summary = "Save Notes Category", tags= {}, description = "Admin can save notes category")
	@PostMapping("/save")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> saveCategory(@Valid @RequestBody CategoryDto categoryDto);
	
	@Operation(summary = "Get All Notes Category", tags= {}, description = "Admin can get all notes category")
	@GetMapping("/")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getAllCategory();
	
	@Operation(summary = "Get Only Active Notes Category", tags= {}, description = "Admin and User can get active notes category")
	@GetMapping("/active")
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> getActiveCategory();
	
	@Operation(summary = "Get All Notes Category By Id", tags= {}, description = "Admin can get notes category by id")
	@GetMapping("/{id}")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws Exception;
	
	@Operation(summary = "Delete Notes Category By Id", tags= {}, description = "Admin can delete notes category by id")
	@DeleteMapping("/{id}")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> deleteCategoryDetailsById(@PathVariable Integer id);
	
}
