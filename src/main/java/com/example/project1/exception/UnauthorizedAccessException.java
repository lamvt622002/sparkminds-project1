package com.example.project1.exception;

public class UnauthorizedAccessException extends RuntimeException{
    public UnauthorizedAccessException(){
        super("You are not authorized to access this resource.");
    }

    public UnauthorizedAccessException(String message){
        super(message);
    }

    public UnauthorizedAccessException(String message, Throwable cause){
        super(message, cause);
    }
}
