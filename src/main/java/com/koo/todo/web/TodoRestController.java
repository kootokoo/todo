package com.koo.todo.web;

import com.koo.todo.application.TodoService;
import com.koo.todo.application.vo.RequestAddTodo;
import com.koo.todo.application.vo.RequestEditTodo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/api/todo")
public class TodoRestController {
    @Autowired
    private TodoService todoService;


    @PostMapping
    public Long addTodo(@RequestBody @Valid RequestAddTodo requestAddTodo) {
        return todoService.add(requestAddTodo);
    }

    @PutMapping
    public Long editTodo(@RequestBody @Valid RequestEditTodo requestAddTodo) {
        return todoService.edit(requestAddTodo);
    }

    @DeleteMapping("/done/{id}")
    public Long doneTodo(@PathVariable @NotNull Long id) {
        return todoService.changeToDone(Long.valueOf(id));
    }
}
