package com.example.project1.exception;

public class InvalidSessionException extends RuntimeException{
    public InvalidSessionException(){
        super("Invalid session");
    }

    public InvalidSessionException(String message){
        super(message);
    }

    public InvalidSessionException(String message, Throwable cause){
        super(message, cause);
    }
}
