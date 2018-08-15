package com.koo.todo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TodoRepository extends JpaRepository<Todo, Long> {
    @Query("SELECT id FROM Todo WHERE id in :ids AND doneAt IS NOT NULL ")
    List<Long> findIdsNotDoneYet(@Param("ids") List<Long> ids);
}
