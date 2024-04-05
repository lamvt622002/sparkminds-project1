package com.example.project1.controllers.advices;

import com.example.project1.exception.AuthExistsEmailExeption;
import com.example.project1.exception.AuthExistsUsernameExeption;
import com.example.project1.exception.DatabaseSaveException;
import com.example.project1.payload.response.ResponseError;
import com.example.project1.repository.ResponseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthControllerAdvice {
    @ExceptionHandler(AuthExistsEmailExeption.class)
    public ResponseEntity<ResponseRepository> authExistsEmailException(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError<>(false, HttpStatus.BAD_REQUEST.value(), "Email already exists in the database"));
    }
    @ExceptionHandler(AuthExistsUsernameExeption.class)
    public ResponseEntity<ResponseRepository> authExistsUsernameException(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError<>(false, HttpStatus.BAD_REQUEST.value(), "Username already exists in the database"));
    }
    @ExceptionHandler(DatabaseSaveException.class)
    public ResponseEntity<ResponseRepository> databaseSaveException(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError<>(false, HttpStatus.BAD_REQUEST.value(), "Something went wrong with database"));
    }
}
