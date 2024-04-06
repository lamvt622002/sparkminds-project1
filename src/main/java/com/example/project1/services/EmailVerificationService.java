package com.example.project1.services;

import com.example.project1.entities.EmailVerification;

public interface EmailVerificationService {
    void findAndDeleteEmailVerificationByTokenAndEmail(String token, String email);
    void saveEmailVerificationToken(EmailVerification emailVerification);
}
