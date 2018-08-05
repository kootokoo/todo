package com.koo.todo.application;

import com.google.common.collect.Lists;
import com.koo.link.application.LinkService;
import com.koo.link.domain.Link;
import com.koo.todo.application.exception.CannotChangeTobeDoneException;
import com.koo.todo.application.exception.CannotMakeLinkBySelf;
import com.koo.todo.application.vo.RequestAddTodo;
import com.koo.todo.application.vo.RequestEditTodo;
import com.koo.todo.application.vo.ResponseTodo;
import com.koo.todo.domain.Todo;
import com.koo.todo.domain.TodoNotFoundException;
import com.koo.todo.domain.TodoRepository;
import com.koo.utils.string.CommaSeparator;
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
        List<Link> linkList = makeLinkList(null, requestAddTodo.getLinks());

        Todo newTodo = Todo.builder()
                .desc(requestAddTodo.getDesc())
                .linkList(linkList)
                .build();

        return todoRepository.save(newTodo).getId();
    }

    private void checkIsAllExist(List<Long> requestLinkIds) {
        List<Long> nonExistId = Lists.newArrayList(requestLinkIds);
        List<Todo> allById = todoRepository.findAllById(requestLinkIds);
        List<Long> foundTodoId = allById.stream()
                .map(Todo::getId)
                .collect(Collectors.toList());

        nonExistId.removeAll(foundTodoId);
        if (!nonExistId.isEmpty()) {
            throw new TodoNotFoundException("존재하지 않는 링크가 포함되어 있습니다 id : " + nonExistId.toString());
        }

    }

    private List<Link> makeLinkList(Long sourceTodoId, String linkListStr) {

        if (linkListStr.isEmpty()) {
            return null;
        } else {
            //extract linkIds
            List<Long> linkIdList = CommaSeparator.comma2list(linkListStr);
            //validation
            checkContainSelfId(sourceTodoId, linkIdList);
            checkIsAllExist(linkIdList);

            return linkIdList.stream()
                    .map(todoId -> new Link(todoId))
                    .collect(Collectors.toList());
        }

    }

    @Transactional
    public Long edit(RequestEditTodo requestEditTodo) {

        Todo found = getTodoById(requestEditTodo.getId());

        List<Link> linkList = makeLinkList(requestEditTodo.getId(), requestEditTodo.getLinks());

        found.updateDescription(requestEditTodo.getDesc());
        found.updateLinks(linkList);
        return todoRepository.save(found).getId();
    }

    private void checkContainSelfId(Long id, List<Long> links) {
        if (links.contains(id)) {
            throw new CannotMakeLinkBySelf("스스로 참조가 될 수 없습니다");
        }
    }

    @Transactional
    public Todo changeToDone(long todoId) {
        Todo found = getTodoById(todoId);
        List<Long> linkedIdListByTodoId = linkService.getLinkedIdListByTodoId(todoId);

        if (linkedIdListByTodoId.isEmpty()) {
            found.markDoneAt();
            return todoRepository.save(found);
        } else {
            throw new CannotChangeTobeDoneException(linkedIdListByTodoId.toString() + "가 존재하여 done 처리 할 수 없습니다.");
        }

    }

    private Todo getTodoById(long todoId) {
        return todoRepository.findById(todoId)
                .orElseThrow(() -> new TodoNotFoundException("id : " + todoId + "not found"));
    }

}
