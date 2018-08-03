package com.koo.todo.web;

import com.koo.todo.application.TodoService;
import com.koo.todo.application.vo.RequestAddTodo;
import com.koo.todo.application.vo.ResponseTodo;
import com.koo.todo.domain.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class TodoController {
    @Autowired
    private TodoService todoService;

    @GetMapping("/")
    public ModelAndView main(ModelAndView modelAndView) {
        modelAndView.setViewName("main");
        return modelAndView;
    }

    @GetMapping("/list")
    public ModelAndView getAll(ModelAndView mnv, Pageable pageable) {
        List<ResponseTodo> todoList = todoService.getTodoList(pageable);
        mnv.setViewName("todo/todo");
        mnv.addObject("todoList", todoList);
        return mnv;
    }


}
