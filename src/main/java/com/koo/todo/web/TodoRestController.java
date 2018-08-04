package com.koo.todo.web;

import com.koo.todo.application.TodoService;
import com.koo.todo.application.vo.RequestAddTodo;
import com.koo.todo.application.vo.RequestEditTodo;
import com.koo.todo.utils.timelistener.string.CommaSeparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TodoRestController {
    @Autowired
    private TodoService todoService;


    @PostMapping("/api/add")
    public Long addTodo(@RequestBody @Valid RequestAddTodo requestAddTodo) {
        return todoService.add(requestAddTodo);
    }

    @PostMapping("/api/edit")
    public Long editTodo(@RequestBody @Valid RequestEditTodo requestAddTodo) {
        return todoService.edit(requestAddTodo);
    }

    @RequestMapping(value="/api/done/{id}", method=RequestMethod.GET, produces="application/json")
    public void editTodo(@PathVariable Long id) {
        todoService.changeToDone(Long.valueOf(id));
    }


}
