package com.koo.todo.web;

import com.koo.todo.application.TodoService;
import com.koo.todo.application.vo.ResponseTodo;
import com.koo.utils.paging.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TodoController {
    @Autowired
    private TodoService todoService;

    @GetMapping("/")
    public String main(Model model, @PageableDefault(size = 5) Pageable pageable ) {
        return getAll(model, pageable);
    }

    @GetMapping("/todos")
    public String getAll(Model model, @PageableDefault(size = 5) Pageable pageable) {
        Page<ResponseTodo> todoPage = todoService.getTodoList(pageable);
        model.addAttribute("todoList", todoPage.getContent());
        model.addAttribute("pagination", Pagination.from(todoPage.getTotalPages(), todoPage.getNumber()));
        model.addAttribute("contentSize", todoPage.getTotalElements());

        return "todo/todo";
    }


}
