package com.koo.todo.application.vo;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class RequestEditTodo {
	@NotNull
	private Long id;
	@NotNull
	private String description;
}
