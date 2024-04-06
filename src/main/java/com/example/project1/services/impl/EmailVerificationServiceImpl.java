package com.example.project1.services.impl;

import com.example.project1.entities.EmailVerification;
import com.example.project1.entities.User;
import com.example.project1.exception.DatabaseAccessException;
import com.example.project1.exception.DataNotFoundExeption;
import com.example.project1.repository.EmailVerificationRepository;
import com.example.project1.services.EmailVerificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmailVerificationServiceImpl implements EmailVerificationService {
    private final EmailVerificationRepository emailVerificationRepository;
    private final UserServiceImpl userService;

    public EmailVerificationServiceImpl(EmailVerificationRepository emailVerificationRepository, UserServiceImpl userService) {
        this.emailVerificationRepository = emailVerificationRepository;
        this.userService = userService;
    }

    @Override
    public void verifyEmail(String token, User user) {
        EmailVerification emailVerification = emailVerificationRepository.findByTokenAndEmail(token, user.getEmail()).orElseThrow(DataNotFoundExeption::new);
        emailVerificationRepository.delete(emailVerification);

        userService.enableUser(user);
    }

    @Override
    public void saveEmailVerificationToken(EmailVerification emailVerification) {
        EmailVerification emailVerify = emailVerificationRepository.save(emailVerification);

        if(emailVerify == null){
            throw new DatabaseAccessException();
        }
    }
}
