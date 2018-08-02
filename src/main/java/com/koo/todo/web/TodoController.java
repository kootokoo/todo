package com.koo.todo.web;

import com.koo.todo.application.TodoService;
import com.koo.todo.domain.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {
	@Autowired
	private TodoService todoService;
	@GetMapping("/hello")
	public String hello() {
		return "HelloWorld";
	}

	@GetMapping("/list")
	public String getAll(Model model, Pageable pageable ) {
		Page<Todo> todoList = todoService.getTodoList(pageable);
		model.addAttribute("todoList",todoList);
		return "";
	}
}
