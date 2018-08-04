package com.koo.todo.application;

import com.google.common.collect.Lists;
import com.koo.link.application.LinkService;
import com.koo.link.domain.Link;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private LinkService linkService;

    public Page<ResponseTodo> getTodoList(Pageable listRequest) {
        return todoRepository.findAll(listRequest).map(ResponseTodo::new);
    }

    @Transactional
    public Long add(RequestAddTodo requestAddTodo) {
        List<Link> linkList = makeLinkList(requestAddTodo);
        // save todo
        Todo newTodo = Todo.builder()
                .description(requestAddTodo.getDescription())
                .link(linkList)
                .build();

        return todoRepository.save(newTodo).getSeq();
    }

    private void checkIsAllExist(List<Long> todoIds) {
        List<Long> foundTodoList = todoRepository.findIdByIdIn(todoIds);
        List<Long> nonExistIds = todoIds.stream().filter(target -> !foundTodoList.contains(target))
                .collect(Collectors.toList());

        if (!nonExistIds.isEmpty()) {
            throw new TodoNotFoundException("존재하지 않는 링크가 포함되어 있습니다 id : " +nonExistIds.toString());
        }

    }

    private List<Link> makeLinkList(RequestAddTodo requestAddTodo) {
        List<Long> linkIdList = CommaSeparator.comma2list(requestAddTodo.getLinkIds());
        checkIsAllExist(linkIdList);

        List<Link> linkList = Lists.newArrayList();

        for (Long linkId : linkIdList) {
            linkList.add(new Link());
        }
        return linkList;
    }

    @Transactional
    public Long edit(RequestEditTodo requestEditTodo) {
        Todo found = getTodoById(requestEditTodo.getId());
        found.updateDescription(requestEditTodo.getDescription());
        return todoRepository.save(found).getSeq();
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
