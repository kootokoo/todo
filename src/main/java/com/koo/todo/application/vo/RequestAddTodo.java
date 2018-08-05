package com.koo.todo.application.vo;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class RequestAddTodo {
	@NotNull
	private String desc;

	private String links;

}
