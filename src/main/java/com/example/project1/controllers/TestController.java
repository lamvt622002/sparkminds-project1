package com.example.project1.controllers;

import com.example.project1.entities.Authorities;
import com.example.project1.payload.response.ResponseMessage;
import com.example.project1.repository.AuthoritiesRepository;
import com.example.project1.payload.response.ResponseRepository;
import com.example.project1.services.impl.TestService;
import com.example.project1.utitilies.JwtUtilily;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private final TestService testService;
    private final JwtUtilily jwtUtilily;

    private final AuthoritiesRepository authoritiesRepository;

    public TestController(TestService testService, JwtUtilily jwtUtilily, AuthoritiesRepository authoritiesRepository) {
        this.testService = testService;
        this.jwtUtilily = jwtUtilily;
        this.authoritiesRepository = authoritiesRepository;
    }

    @GetMapping("/test")
    public ResponseEntity<ResponseRepository> test(){
        System.out.println("ddd");

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage<>(true, HttpStatus.OK.value(), "he"));
    }

    @GetMapping("/test-lombok")
    public ResponseEntity<ResponseRepository> testLombok(){
       

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage<>(true, HttpStatus.OK.value(), "heheheh"));
    }
}
