package com.example.project1.controllers.advices;

import com.example.project1.entities.ResponseError;
import com.example.project1.exception.TestException;
import com.example.project1.repository.ResponseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(TestException.class)
    public ResponseEntity<ResponseRepository> testException(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError<>(false, HttpStatus.BAD_REQUEST.value(), "test exception successfully"));
    }
}
