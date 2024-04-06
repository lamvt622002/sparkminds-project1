package com.example.project1.services.impl;

import com.example.project1.entities.EmailVerification;
import com.example.project1.exception.DataBaseAccessException;
import com.example.project1.exception.DataNotFoundExeption;
import com.example.project1.repository.EmailVerificationRepository;
import com.example.project1.services.EmailVerificationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {
    private final EmailVerificationRepository emailVerificationRepository;

    public EmailVerificationServiceImpl(EmailVerificationRepository emailVerificationRepository) {
        this.emailVerificationRepository = emailVerificationRepository;
    }

    @Override
    @Transactional
    public void findAndDeleteEmailVerificationByTokenAndEmail(String token, String email) {
        EmailVerification emailVerification = emailVerificationRepository.findByTokenAndEmail(token, email).orElseThrow((DataNotFoundExeption::new));
        emailVerificationRepository.delete(emailVerification);

        boolean isDelete = emailVerificationRepository.existsById(emailVerification.getId());

        if(!isDelete){
            throw new DataBaseAccessException();
        }

    }

    @Override
    public void saveEmailVerificationToken(EmailVerification emailVerification) {
        EmailVerification emailVerify = emailVerificationRepository.save(emailVerification);

        if(emailVerify == null){
            throw new DataBaseAccessException();
        }
    }
}
