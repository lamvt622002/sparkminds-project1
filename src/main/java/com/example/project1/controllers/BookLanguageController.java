package com.example.project1.controllers;

import com.example.project1.mapper.BookLanguageMapper;
import com.example.project1.mapper.CategoryMapper;
import com.example.project1.payload.response.ResponseMessage;
import com.example.project1.payload.response.ResponseRepository;
import com.example.project1.repository.BookLanguageRepository;
import com.example.project1.repository.CategoryRepository;
import com.example.project1.services.BookLanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/language")
@RequiredArgsConstructor
public class BookLanguageController {
    private final BookLanguageService bookLanguageService;

    @GetMapping("/all-language")
    public ResponseEntity<ResponseRepository> allBookLanguage(){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage<>(true, HttpStatus.OK.value(), bookLanguageService.getAllBookLanguage()));
    }
}
