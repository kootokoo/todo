package com.koo.todo.application;

import com.google.common.collect.Lists;
import com.koo.link.application.LinkService;
import com.koo.link.domain.Link;
import com.koo.link.domain.LinkRepository;
import com.koo.todo.application.vo.RequestAddTodo;
import com.koo.todo.application.vo.RequestEditTodo;
import com.koo.todo.application.vo.ResponseTodo;
import com.koo.todo.domain.Todo;
import com.koo.todo.domain.TodoNotFoundException;
import com.koo.todo.domain.TodoRepository;
import com.koo.todo.utils.timelistener.string.CommaSeparator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private LinkService linkService;

    public Page<ResponseTodo> getTodoList(Pageable listRequest) {
        return todoRepository.findAll(listRequest).map(
                todo -> new ResponseTodo(todo, linkService.getLinkedTodoList(todo.getId()))
        );
    }

    @Transactional
    public Long add(RequestAddTodo requestAddTodo) {
        List<Long> linkIdList = CommaSeparator.comma2list(requestAddTodo.getLinkIds());
        List<Link> linkList = Lists.newArrayList();

        for(Long linkId : linkIdList) {
            linkList.add(new Link(linkId));
        }

        Todo newTodo = Todo.builder()
                .description(requestAddTodo.getDescription())
                .link(linkList)
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
