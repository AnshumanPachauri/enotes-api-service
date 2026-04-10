package com.ap.enotes_api_service.service;

import java.util.List;

import com.ap.enotes_api_service.dto.TodoDto;

public interface TodoService {

	public Boolean saveTodo(TodoDto todoDto);
	
	public TodoDto getTodoByid(Integer id) throws Exception;
	
	public List<TodoDto> getTodoByUser() throws Exception;
	
}
