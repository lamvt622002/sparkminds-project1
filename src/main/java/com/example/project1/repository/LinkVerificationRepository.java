package com.example.project1.repository;

import com.example.project1.entities.LinkVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinkVerificationRepository extends JpaRepository<LinkVerification, Long> {
    Optional<LinkVerification> findByTokenAndEmail(String token, String email);

}
