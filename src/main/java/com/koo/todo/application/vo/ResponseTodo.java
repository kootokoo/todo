package com.koo.todo.application.vo;


import com.koo.link.domain.Link;
import com.koo.todo.domain.Todo;
import com.koo.utils.string.TimeToStringHelper;
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
    private List<Long> linkList;
    private String createdAt;
    private String modifiedAt;
    private String doneAt;

    public ResponseTodo(Todo todo) {
        this.id = todo.getId();
        this.desc = todo.getDesc();
        this.linkList = todo.getLinkList().stream().map(Link::getLinkedId).collect(Collectors.toList());;
        this.createdAt = TimeToStringHelper.convert(todo.getCreatedAt());
        this.modifiedAt = TimeToStringHelper.convert(todo.getModifiedAt());
        this.doneAt = TimeToStringHelper.convert(todo.getDoneAt());
    }
}
