package com.example.project1.controllers;

import com.example.project1.payload.response.ResponseMessage;
import com.example.project1.repository.ResponseRepository;
import com.example.project1.services.impl.TestService;
import com.example.project1.utitilies.JwtUtilily;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    private final TestService testService;
    private final JwtUtilily jwtUtilily;

    public TestController(TestService testService, JwtUtilily jwtUtilily) {
        this.testService = testService;
        this.jwtUtilily = jwtUtilily;
    }

    @GetMapping("/test")
    public ResponseEntity<ResponseRepository> test(){
        Map<String, Object> claims = new HashMap<>();

        String token = jwtUtilily.generateTokenForEmail("lam@gmail.com");

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage<>(true, HttpStatus.OK.value(), token));
    }
}
