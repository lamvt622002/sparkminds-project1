package com.example.project1.repository;

import com.example.project1.entities.EmailVerification;
import com.example.project1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {
    Optional<EmailVerification> findByTokenAndEmail(String token, String email);

}
