package com.koo.link.application.exception;

public class AlreadLinkedTodoExist extends RuntimeException{
    public AlreadLinkedTodoExist(String message) {
        super(message);
    }
}
