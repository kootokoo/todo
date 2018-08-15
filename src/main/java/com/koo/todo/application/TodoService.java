package com.koo.todo.application;

import com.google.common.collect.Lists;
import com.koo.link.application.LinkService;
import com.koo.link.domain.Link;
import com.koo.todo.application.exception.CannotChangeTobeDoneException;
import com.koo.todo.application.exception.CannotMakeLinkBySelf;
import com.koo.todo.application.exception.MakeLinkCirculationException;
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
import javax.validation.constraints.NotNull;
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
        List<Link> linkList = makeLinkList(requestAddTodo.getLinks());

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

    private List<Link> makeLinkList(String linkListStr) {
        if (linkListStr.isEmpty()) {
            return null;
        } else {
            //extract linkIds
            List<Long> linkIds = CommaSeparator.comma2list(linkListStr);
            //validation
            checkIsAllExist(linkIds);
            //make new links
            return makeLinks(linkIds);
        }
    }

    private List<Link> getUpdatableLinkList(Long sourceTodoId, String linkListStr) {
        if (linkListStr.isEmpty()) {
            return null;
        } else {
            //extract linkIds
            List<Long> updateLinkIds = CommaSeparator.comma2list(linkListStr);

            //validation
            checkCirculation(sourceTodoId,updateLinkIds);

            checkContainSelfId(sourceTodoId, updateLinkIds);
            checkIsAllExist(updateLinkIds);

            //기존 저장된 old link Ids delete
            deleteAllByTodoId(sourceTodoId);

            //make new links
            return makeLinks(updateLinkIds);
        }

    }

    private void checkCirculation(Long sourceTodoId, List<Long> updateLinkIds) {
        List<Link> linkedByTodoId = linkService.getLinkListByTodoId(sourceTodoId);

        List<Long> canMakeCycleId = linkedByTodoId.stream()
                .map(Link::getTodoId)
                .filter(todoId -> updateLinkIds.contains(todoId))
                .collect(Collectors.toList());

        if(!canMakeCycleId.isEmpty()) {
            throw new MakeLinkCirculationException(canMakeCycleId.toString() + "가 순환참조 됩니다.");
        }

    }

    private void deleteAllByTodoId(Long sourceTodoId) {
        linkService.deleteAllByTodoId(sourceTodoId);
    }

    private List<Link> makeLinks(List<Long> copiedNewIds) {
        return copiedNewIds.stream()
                .map(todoId -> new Link(todoId))
                .collect(Collectors.toList());
    }

    @Transactional
    public Long edit(RequestEditTodo requestEditTodo) {
        long id = requestEditTodo.getId();
        Todo found = getTodoById(id);

        List<Link> updatableLinkList = getUpdatableLinkList(id, requestEditTodo.getLinks());

        found.updateDescription(requestEditTodo.getDesc());
        found.updateLinks(updatableLinkList);
        return todoRepository.save(found).getId();
    }

    private void checkContainSelfId(Long id, List<Long> links) {
        if (links.contains(id)) {
            throw new CannotMakeLinkBySelf("스스로 참조가 될 수 없습니다");
        }
    }

    @Transactional
    public Long changeToDone(long todoId) {
        Todo found = getTodoById(todoId);
        List<Long> linkedIdList = linkService.getLinkedIdListByTodoId(todoId);
        List<Long> notDoneTodoIdList = todoRepository.findIdsNotDoneYet(linkedIdList);

        if (canBeDone(notDoneTodoIdList)) {
            found.markDoneAt();
            return todoRepository.save(found).getId();
        } else {
            throw new CannotChangeTobeDoneException(notDoneTodoIdList.toString() + "가 done 되지 않았습니다");
        }

    }


    private boolean canBeDone(List<Long> nonDoneStatusList) {
        return nonDoneStatusList.isEmpty();
    }


    private Todo getTodoById(long todoId) {
        return todoRepository.findById(todoId)
                .orElseThrow(() -> new TodoNotFoundException("id : " + todoId + "not found"));
    }

}
