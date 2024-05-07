package com.example.project1.controllers;

import com.example.project1.mapper.CategoryMapper;
import com.example.project1.payload.response.ResponseMessage;
import com.example.project1.payload.response.ResponseRepository;
import com.example.project1.repository.CategoryRepository;
import com.example.project1.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/all-category")
    public ResponseEntity<ResponseRepository> allCategory(){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage<>(true, HttpStatus.OK.value(),  categoryService.getAllCategory()));
    }
}
