package com.example.project1.controllers;

import com.example.project1.services.LinkVerificationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LinkVerificationController {
   private final LinkVerificationService linkVerificationService;

    @GetMapping("/verify-register/{token}")
    public String verifyEmail(@PathVariable String token, Model theModel){
      return linkVerificationService.verifyLink(token, theModel);
    }

    @GetMapping("/verify-change-email/{token}")
    public String verifyChangeEmail(@PathVariable String token, HttpServletRequest request, Model theModel){
        return linkVerificationService.verifyLinkChangeEmail(token, request, theModel);
    }
}
