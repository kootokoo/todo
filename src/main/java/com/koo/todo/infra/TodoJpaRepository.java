package com.koo.todo.infra;

import com.koo.todo.domain.Todo;
import com.koo.todo.domain.TodoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

//@Repository
public interface TodoJpaRepository extends JpaRepository<Todo, Long>, TodoRepository{
}
