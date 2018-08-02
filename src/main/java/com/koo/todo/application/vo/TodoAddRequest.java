package com.koo.todo.application.vo;

import lombok.Getter;

import java.util.List;

@Getter
public class TodoAddRequest {
	private String desc;
	private List<Long> links;
}
