package com.example.project1.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(){
        super("Bad request exception");
    }

    public BadRequestException(String message){
        super(message);
    }

    public BadRequestException(String message, Throwable cause){
        super(message,cause);
    }
}
