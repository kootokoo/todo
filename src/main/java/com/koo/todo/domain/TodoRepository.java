package com.koo.todo.domain;

import com.koo.link.domain.Link;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface TodoRepository {
	Todo save(Todo todo);
	Optional<Todo> findById(long id);
	Page<Todo> findAll(Pageable pageable);

    boolean isExistIds(List<Link> linkList);

	List<Long> findIdByIdIn(List<Long> todoIds);
}
