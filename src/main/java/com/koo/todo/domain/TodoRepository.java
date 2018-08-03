package com.koo.todo.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface TodoRepository {
	Todo save(Todo todo);
	Optional<Todo> findById(long id);
	Page<Todo> findAll(Pageable pageable);
}
