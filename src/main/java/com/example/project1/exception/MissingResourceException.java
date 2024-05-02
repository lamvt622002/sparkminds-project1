package com.example.project1.exception;

public class MissingResourceException extends RuntimeException{
    public MissingResourceException(){
        super("Something went wrong");
    }

    public MissingResourceException(String message){
        super(message);
    }

    public MissingResourceException(String message, Throwable cause){
        super(message, cause);
    }
}
