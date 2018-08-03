package com.koo.todo.web;

import com.koo.todo.application.TodoService;
import com.koo.todo.application.vo.RequestAddTodo;
import com.koo.todo.application.vo.RequestEditTodo;
import com.koo.todo.domain.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController("/api")
public class TodoRestController {
    @Autowired
    private TodoService todoService;


    @PostMapping("/add")
    public void addTodo(@RequestBody RequestAddTodo requestAddTodo) {
        todoService.add(requestAddTodo);
    }

    @PostMapping("/edit")
    public void editTodo(@RequestBody RequestEditTodo requestAddTodo) {
        todoService.edit(requestAddTodo);
    }

}
