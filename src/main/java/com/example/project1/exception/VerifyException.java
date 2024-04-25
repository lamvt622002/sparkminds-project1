package com.example.project1.exception;

public class VerifyException extends RuntimeException{
    public VerifyException(){
        super("Data not found");
    }

    public VerifyException(String message){
        super(message);
    }

    public VerifyException(String message, Throwable cause){
        super(message, cause);
    }
}
