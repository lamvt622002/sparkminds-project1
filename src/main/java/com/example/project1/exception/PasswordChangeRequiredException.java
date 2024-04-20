package com.example.project1.exception;

public class PasswordChangeRequiredException extends RuntimeException{
    public PasswordChangeRequiredException(){
        super("Data not found");
    }

    public PasswordChangeRequiredException(String message){
        super(message);
    }

    public PasswordChangeRequiredException(String message, Throwable cause){
        super(message, cause);
    }
}
