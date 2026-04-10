package com.ap.enotes_api_service.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ap.enotes_api_service.dto.TodoDto;
import com.ap.enotes_api_service.entity.Todo;
import com.ap.enotes_api_service.exception.ResourceNotFoundException;
import com.ap.enotes_api_service.repository.TodoRepository;
import com.ap.enotes_api_service.service.TodoService;
import com.ap.enotes_api_service.utils.Validation;

@Service
public class TodoServiceImpl implements TodoService {

	@Autowired
	private TodoRepository todoRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private Validation validation;
	
	@Override
	public Boolean saveTodo(TodoDto todoDto) throws Exception {
		
		//Validation for todo Status.
		
		validation.todoValidation(todoDto);
		
		Todo mappedTodo = modelMapper.map(todoDto, Todo.class);
		mappedTodo.setStatusId(todoDto.getStatus().getId());
		Todo savedTodo = todoRepository.save(mappedTodo);
		
		if(ObjectUtils.isEmpty(savedTodo)) {
			return false;
		}
		return true;
	}

	@Override
	public TodoDto getTodoByid(Integer id) throws Exception {
		
		Todo byId = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id = " + id));
		TodoDto mappedTodoDto = modelMapper.map(byId, TodoDto.class);
		return mappedTodoDto;
	}

	@Override
	public List<TodoDto> getTodoByUser() {
		
		Integer userId = 2;
		List<Todo> todoList =  todoRepository.findByCreatedBy(userId);
		
		return todoList.stream().map(todo -> modelMapper.map(todo, TodoDto.class)).toList();
		
	}

}
