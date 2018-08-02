package com.koo.todo.domain;

public class TodoNotFoundException extends RuntimeException{
	public TodoNotFoundException(String message) {
		super(message);
	}
}
