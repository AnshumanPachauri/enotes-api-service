package com.ap.enotes_api_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ap.enotes_api_service.dto.TodoDto;
import com.ap.enotes_api_service.service.TodoService;
import com.ap.enotes_api_service.utils.CommonUtil;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {

	@Autowired
	private TodoService todoService;
	
	@PostMapping("/")
	public ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) throws Exception{
		
		Boolean saveTodo = todoService.saveTodo(todoDto);
		if(saveTodo) {
			return CommonUtil.CreateBuildResponseMessage("Todo Saved", HttpStatus.OK);
		}
		return CommonUtil.CreateErrorResponseMessage("Todo Not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getTodoById(@PathVariable Integer id) throws Exception{
		
		TodoDto todoById = todoService.getTodoByid(id);
		return CommonUtil.CreateBuildResponse(todoById, HttpStatus.OK);
	}
	
	@GetMapping("/user-todo")
	public ResponseEntity<?> getTodoByUser() throws Exception{
		
		List<TodoDto> todosByUser = todoService.getTodoByUser();
		
		if(CollectionUtils.isEmpty(todosByUser)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.CreateBuildResponse(todosByUser, HttpStatus.OK);
	}
	
}
