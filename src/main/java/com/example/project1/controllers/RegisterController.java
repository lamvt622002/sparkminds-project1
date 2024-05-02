package com.example.project1.controllers;

import com.example.project1.payload.request.RegisterRequest;
import com.example.project1.services.RegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RegisterController {
    private final RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest registerRequest){
        registerService.register(registerRequest);

        return  ResponseEntity.noContent().build();
    }
}
