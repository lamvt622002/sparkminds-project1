package com.example.project1.controllers;

import com.example.project1.mapper.PublisherMapper;
import com.example.project1.payload.response.ResponseMessage;
import com.example.project1.payload.response.ResponseRepository;
import com.example.project1.repository.PublisherRepository;
import com.example.project1.services.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/publisher")
@RequiredArgsConstructor
public class PublisherController {
    private final PublisherService publisherService;

    @GetMapping("/all-publisher")
    public ResponseEntity<ResponseRepository> allPublisher(){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage<>(true, HttpStatus.OK.value(), publisherService.getAllPublisher()));
    }
}
