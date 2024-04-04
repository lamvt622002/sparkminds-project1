package com.example.project1.controllers;

import com.example.project1.entities.ResponseMessage;
import com.example.project1.repository.ResponseRepository;
import com.example.project1.services.TestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/test")
    public ResponseEntity<ResponseRepository> test(){
        testService.test();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage<>(true, HttpStatus.OK.value(), "data here"));
    }
}
