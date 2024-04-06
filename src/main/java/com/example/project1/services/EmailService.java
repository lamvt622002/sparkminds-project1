package com.example.project1.services;

public interface EmailService {
    void sendMessage(String to, String subject, String text);
}
