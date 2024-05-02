package com.example.project1.controllers.advices;

import com.example.project1.exception.*;
import com.example.project1.payload.response.ResponseError;
import com.example.project1.payload.response.ResponseRepository;
import com.twilio.exception.ApiException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

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

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ResponseRepository> dataNotFoundExeption(DataNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseError<>(false, HttpStatus.NOT_FOUND.value(),ex.getMessage() ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseRepository> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();

        ex.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError<>(false, HttpStatus.NOT_FOUND.value(), errors));
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseRepository> httpMessageNotReadableException(HttpMessageNotReadableException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError<>(false, HttpStatus.BAD_REQUEST.value(),ex.getMessage() ));
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseRepository> BadCredentialsException(BadCredentialsException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError<>(false, HttpStatus.BAD_REQUEST.value(),ex.getMessage() ));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ResponseRepository> expiredJwtException(ExpiredJwtException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseError<>(false, HttpStatus.UNAUTHORIZED.value(),ex.getMessage() ));
    }

    @ExceptionHandler(SQLSyntaxErrorException.class)
    public ResponseEntity<ResponseRepository> sqlSyntaxErrorException(SQLSyntaxErrorException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseError<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage() ));
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ResponseRepository> unauthorizedAccessException(UnauthorizedAccessException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseError<>(false, HttpStatus.UNAUTHORIZED.value(),ex.getMessage() ));
    }
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ResponseRepository> apiException(ApiException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseError<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage() ));
    }

    @ExceptionHandler(InvalidSessionException.class)
    public ResponseEntity<ResponseRepository> invalidSessionException(InvalidSessionException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseError<>(false, 40101,ex.getMessage() ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseRepository> exception(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseError<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage() ));
    }
}
