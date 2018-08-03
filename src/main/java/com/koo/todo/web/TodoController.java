package com.koo.todo.web;

import com.koo.todo.application.TodoService;
import com.koo.todo.application.vo.RequestAddTodo;
import com.koo.todo.application.vo.ResponseTodo;
import com.koo.todo.domain.Todo;
import com.koo.todo.utils.timelistener.paging.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class TodoController {
    @Autowired
    private TodoService todoService;

    @GetMapping("/")
    public ModelAndView main(ModelAndView mnv, @PageableDefault(size = 5) Pageable pageable ) {
        return getAll(mnv, pageable);
    }

    @GetMapping("/list")
    public ModelAndView getAll(ModelAndView mnv, @PageableDefault(size = 5) Pageable pageable) {
        Page<ResponseTodo> todoPage = todoService.getTodoList(pageable);
        mnv.setViewName("todo/todo");
        mnv.addObject("todoList", todoPage.getContent());
        mnv.addObject("pagination", Pagination.from(todoPage.getTotalPages(), todoPage.getNumber()));
        mnv.addObject("contentSize", todoPage.getContent().size());

        return mnv;
    }


}
