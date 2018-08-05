package com.koo.todo.application.exception;

public class CannotChangeTobeDoneException extends RuntimeException{
    public CannotChangeTobeDoneException(String message) {
        super(message);
    }
}
