package com.ap.enotes_api_service.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ap.enotes_api_service.dto.TodoDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static com.ap.enotes_api_service.utils.Constants.ROLE_USER;

@Tag(name="Todo", description = "Contains all the Todo Operation api's")
public interface TodoControllerEndpoint {

	
	@Operation(summary = "Save Todo's", tags= {"User", "Todo"}, description = "User can save their todo tasks")
	@PostMapping("/")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) throws Exception;
	
	
	@Operation(summary = "Get Todo's By Todo-Id", tags= {"User", "Todo"}, description = "User can get their todo tasks by todo-id")
	@GetMapping("/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getTodoById(@PathVariable Integer id) throws Exception;
	
	
	@Operation(summary = "Get Todo's By User", tags= {"User", "Todo"}, description = "User can get their todo tasks")
	@GetMapping("/user-todo")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getTodoByUser() throws Exception;
	
}
