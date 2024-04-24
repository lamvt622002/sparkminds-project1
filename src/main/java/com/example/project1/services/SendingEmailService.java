package com.example.project1.services;

import com.example.project1.entities.OtpVerification;
import com.example.project1.entities.User;

public interface SendingEmailService {
    void sendMessage(String to, String subject, String text);

    void sendVerificationEmail(User user);

    void sendVerificationChangeEmail(User user, String newEmail);

    void sendResetPassword(User user, String password);

    void sentOtpVerification(User user);
}
