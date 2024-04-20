package com.example.project1.controllers;

import com.example.project1.services.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class EmailVerificationController {
   private final EmailVerificationService emailVerificationService;

    @GetMapping("/verify-register/{token}")
    public String verifyEmail(@PathVariable String token, Model theModel){
      return emailVerificationService.verifyEmail(token, theModel);
    }
}
