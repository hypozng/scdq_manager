package com.scdq.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scdq.manager.dao.TodoDao;
import com.scdq.manager.model.Todo;

@Service
public class TodoService {
	@Autowired
	private TodoDao todoDao;
	
	/**
	 * 获取所有待办
	 * @return
	 */
	public List<Todo> getTodos() {
		return todoDao.findAll();
	}
	
	/**
	 * 添加待办
	 * @param todo
	 * @return
	 */
	public long addTodo(Todo todo) {
		return todoDao.insert(todo);
	}
	
	/**
	 * 修改待办信息
	 * @param todo
	 * @return
	 */
	public boolean updateTodo(Todo todo) {
		return todoDao.update(todo) > 0;
	}
	
	/**
	 * 删除待办
	 * @return
	 */
	public boolean deleteTodo(long todoId) {
		return todoDao.delete(todoId) > 0;
	}
}
