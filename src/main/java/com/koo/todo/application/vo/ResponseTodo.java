package com.koo.todo.application.vo;


import com.koo.todo.domain.Todo;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Getter
public class ResponseTodo {
    private long id;
    private String description;
    private String createdAt;
    private String modifiedAt;
    private String doneAt;

    public ResponseTodo(Todo todo) {
        this.id = todo.getId();
        this.description = todo.getDescription();
        this.createdAt = toStringDateTime(todo.getCreatedAt());
        this.modifiedAt = toStringDateTime(todo.getModifiedAt());
        this.doneAt = toStringDateTime(todo.getDoneAt());
    }


    private String toStringDateTime(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Optional.ofNullable(localDateTime)
                .map(formatter::format)
                .orElse("");
    }
}
