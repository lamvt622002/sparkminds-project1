package com.example.project1.repository;

import com.example.project1.entities.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpVerificationRepository extends JpaRepository<OtpVerification, Long> {
    Optional<OtpVerification> findByIdAndOtp(Long id, String otp);

    Optional<OtpVerification> findByUserIdAndOtp(Long userId, String otp);
}
