package com.example.project1.repository;

import com.example.project1.entities.OtpChangePhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpChangePhoneRepository extends JpaRepository<OtpChangePhone, Long> {
    Optional<OtpChangePhone> findByUserIdAndOtp(Long userId, String otp);
}
