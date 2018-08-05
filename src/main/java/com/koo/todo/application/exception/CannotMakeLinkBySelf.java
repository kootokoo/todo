package com.koo.todo.application.exception;

public class CannotMakeLinkBySelf extends RuntimeException{
    public CannotMakeLinkBySelf(String message) {
        super(message);
    }
}
