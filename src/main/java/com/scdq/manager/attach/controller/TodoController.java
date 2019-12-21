package com.scdq.manager.attach.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scdq.manager.attach.model.Todo;
import com.scdq.manager.attach.service.TodoService;

@RestController
@RequestMapping("/todo")
public class TodoController {
	
	@Autowired
	private TodoService todoService;
	
	@RequestMapping("/getTodos")
	public List<Todo> getTodos() {
		return todoService.getTodos();
	}
	
	@RequestMapping("/addTodo")
	public long addTodos(Todo todo) {
		return todoService.addTodo(todo);
	}
	
	@RequestMapping("/updateTodo")
	public boolean updateTodo(Todo todo) {
		return todoService.updateTodo(todo);
	}
	
	@RequestMapping("/deleteTodo")
	public boolean deleteTodo(long todoId) {
		return todoService.deleteTodo(todoId);
	}
}
