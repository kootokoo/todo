package com.koo.link.application;

import com.koo.link.application.exception.AlreadLinkedTodoExist;
import com.koo.link.domain.Link;
import com.koo.link.domain.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LinkService {
    @Autowired
    private LinkRepository linkRepository;

    public List<Long> getLinkedIdListByTodoId(Long todoId) {
        //id를 참조로 가지고 있는 대상을 조회 한다.
        List<Link> byLinkedId = linkRepository.findByLinkedId(todoId);
        //id를 참조하고 있는 to_do ids를 반환 한다.
        return byLinkedId.stream().map(Link::getTodoId).collect(Collectors.toList());
    }

    public void deleteAllByTodoId(Long sourceTodoId) {
        linkRepository.deleteByTodoId(sourceTodoId);
    }
}
