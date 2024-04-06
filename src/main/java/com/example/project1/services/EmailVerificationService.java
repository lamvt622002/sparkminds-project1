package com.example.project1.services;

import com.example.project1.entities.EmailVerification;
import com.example.project1.entities.User;

public interface EmailVerificationService {
    void verifyEmail(String token, User user);
    void saveEmailVerificationToken(EmailVerification emailVerification);
}
