package com.example.project1.services;

import com.example.project1.entities.EmailVerification;
import com.example.project1.entities.User;
import org.springframework.ui.Model;

import java.util.Optional;

public interface EmailVerificationService {
    String verifyEmail(String token, Model theModel);
}
