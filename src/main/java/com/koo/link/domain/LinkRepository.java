package com.koo.link.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {
    List<Link> findByLinkedId(Long todoId);
    List<Link> findByTodoId(long todoId);


    void deleteByTodoId(Long sourceTodoId);
}
