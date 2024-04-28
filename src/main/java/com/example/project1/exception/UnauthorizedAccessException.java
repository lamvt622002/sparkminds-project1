package com.example.project1.exception;

import com.example.project1.utitilies.MessagesUtils;

public class UnauthorizedAccessException extends RuntimeException{
    private String message;

    public UnauthorizedAccessException(String errorCode, Object... var2) {
        this.message = MessagesUtils.getMessage(errorCode, var2);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
