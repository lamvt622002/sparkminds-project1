package com.example.project1.exception;

public class DatabaseAccessException extends RuntimeException{
    public DatabaseAccessException(){
        super("An error occurred while accessing the database");
    }
    public DatabaseAccessException(String message){
        super(message);
    }

    public DatabaseAccessException(String message, Throwable cause){
        super(message, cause);
    }
}
