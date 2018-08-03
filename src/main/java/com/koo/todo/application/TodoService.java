package com.koo.todo.application;

import com.koo.todo.application.vo.RequestAddTodo;
import com.koo.todo.application.vo.RequestEditTodo;
import com.koo.todo.application.vo.ResponseTodo;
import com.koo.todo.domain.Todo;
import com.koo.todo.domain.TodoNotFoundException;
import com.koo.todo.domain.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public List<ResponseTodo> getTodoList(Pageable listRequest) {
        return todoRepository.findAll(listRequest).getContent()
                .stream().map(ResponseTodo::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long add(RequestAddTodo requestAddTodo) {
        Todo newTodo = Todo.builder()
                .description(requestAddTodo.getDescription())
                .build();
        return todoRepository.save(newTodo).getId();
    }

    @Transactional
    public Long edit(RequestEditTodo requestEditTodo) {
        Todo found = getTodoById(requestEditTodo.getId());
        found.updateDescription(requestEditTodo.getDescription());
        return todoRepository.save(found).getId();
    }

    @Transactional
    public void changeToDone(long todoId) {
        Todo found = getTodoById(todoId);
        found.done();
        todoRepository.save(found);
    }

    private Todo getTodoById(long todoId) {
        return todoRepository.findById(todoId)
                .orElseThrow(() -> new TodoNotFoundException("id : " + todoId + "not found"));
    }

}
