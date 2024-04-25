package com.example.project1.exception;

public class DataIntegrityViolationException extends RuntimeException{
    public DataIntegrityViolationException(){
        super("Data already exists in database");
    }

    public DataIntegrityViolationException(String message){
        super(message);
    }

    public DataIntegrityViolationException(String message, Throwable cause){
        super(message, cause);
    }
}
