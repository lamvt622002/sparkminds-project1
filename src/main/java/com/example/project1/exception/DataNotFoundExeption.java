package com.example.project1.exception;

public class DataNotFoundExeption extends RuntimeException{
    public DataNotFoundExeption(){
        super("Data not found");
    }

    public DataNotFoundExeption(String message){
        super(message);
    }

    public DataNotFoundExeption(String message, Throwable cause){
        super(message, cause);
    }
}
