package com.example.project1.controllers.advices;

import com.example.project1.exception.BadRequestException;
import com.example.project1.exception.DataIntegrityViolationException;
import com.example.project1.exception.DataNotFoundExeption;
import com.example.project1.exception.DatabaseAccessException;
import com.example.project1.payload.response.ResponseError;
import com.example.project1.repository.ResponseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseRepository> badRequestException(BadRequestException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError<>(false, HttpStatus.BAD_REQUEST.value(),ex.getMessage() ));
    }

    @ExceptionHandler(DatabaseAccessException.class)
    public ResponseEntity<ResponseRepository> databaseAccessException(DatabaseAccessException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseError<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage() ));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseRepository> dataIntegrityViolationException(DataIntegrityViolationException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseError<>(false, HttpStatus.CONFLICT.value(),ex.getMessage() ));
    }

    @ExceptionHandler(DataNotFoundExeption.class)
    public ResponseEntity<ResponseRepository> dataNotFoundExeption(DataNotFoundExeption ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseError<>(false, HttpStatus.NOT_FOUND.value(),ex.getMessage() ));
    }
}
