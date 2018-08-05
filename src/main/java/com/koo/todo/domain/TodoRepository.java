package com.koo.todo.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Long> findIdByIdInAndDoneAtNull(List<Long> ids);
}
