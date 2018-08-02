package com.koo.todo.application.vo;

import lombok.Getter;

import java.util.List;

@Getter
public class TodoEditRequest {
	private long todoId;
	private String desc;
	private List<Long> links;
}
