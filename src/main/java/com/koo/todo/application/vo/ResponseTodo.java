package com.koo.todo.application.vo;


import com.koo.todo.domain.Todo;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Getter
public class ResponseTodo {
    private long id;
    private String desc;
    private String linkList;
    private String createdAt;
    private String modifiedAt;
    private String doneAt;
    private boolean done;

    public ResponseTodo(Todo todo) {
        this.id = todo.getId();
        this.desc = todo.getDesc();
        this.linkList = todo.getCommaLinks();
        this.createdAt = toStringDateTime(todo.getCreatedAt());
        this.modifiedAt = toStringDateTime(todo.getModifiedAt());
        this.doneAt = toStringDateTime(todo.getDoneAt());
        this.done = todo.isDone();
    }


    private String toStringDateTime(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Optional.ofNullable(localDateTime)
                .map(formatter::format)
                .orElse("");
    }
}
