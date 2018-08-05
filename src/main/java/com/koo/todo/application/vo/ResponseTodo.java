package com.koo.todo.application.vo;


import com.koo.todo.domain.Todo;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class ResponseTodo {
    private long id;
    private String desc;
    private String links;
    private String createdAt;
    private String modifiedAt;
    private String doneAt;
    private boolean isDone;

    public ResponseTodo(Todo todo) {
        this.id = todo.getId();
        this.desc = todo.getDesc();
        this.links = todo.getCommaLinks();
        this.createdAt = toStringDateTime(todo.getCreatedAt());
        this.modifiedAt = toStringDateTime(todo.getModifiedAt());
        this.doneAt = toStringDateTime(todo.getDoneAt());
        this.isDone = todo.isDone();
    }


    private String toStringDateTime(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Optional.ofNullable(localDateTime)
                .map(formatter::format)
                .orElse("");
    }
}
