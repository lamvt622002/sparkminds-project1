package com.example.project1.controllers;

import com.example.project1.entities.Book;
import com.example.project1.entities.Test;
import com.example.project1.mapper.BookMapper;
import com.example.project1.mapper.TestMapper;
import com.example.project1.payload.dto.BookDto;
import com.example.project1.payload.dto.TestDto;
import com.example.project1.payload.response.ResponseMessage;
import com.example.project1.payload.response.ResponseRepository;
import com.example.project1.repository.BookRepository;
import com.example.project1.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class TestController {
    private final TestRepository testRepository;

    private final TestMapper testMapper;

    private final BookMapper bookMapper;

    private final BookRepository bookRepository;

    private final PasswordEncoder passwordEncoder;
    @GetMapping("/test")
    public ResponseEntity<ResponseRepository> test(){
        Test test = new Test("Lam");
        testRepository.save(test);


        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage<>(true, HttpStatus.OK.value(),test));
    }

    @GetMapping("/test1")
    public ResponseEntity<ResponseRepository> test1(){
        TestDto testDto = testMapper.testToTestDto(testRepository.findById(1L).get());

        System.out.println(testDto);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage<>(true, HttpStatus.OK.value(),testDto));
    }

    @GetMapping("/test2")
    public ResponseEntity<ResponseRepository> test2(){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage<>(true, HttpStatus.OK.value(),passwordEncoder.encode("123@Admin")));//$2a$10$fnN6DvksHY6CF6vBqojwMuNKfjJkS1yn2e1RgZorUl0qs8.X1221u
    }

}
