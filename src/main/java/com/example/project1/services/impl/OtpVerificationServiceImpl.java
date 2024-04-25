package com.example.project1.services.impl;

import com.example.project1.entities.OtpVerification;
import com.example.project1.entities.User;
import com.example.project1.enums.UserStatus;
import com.example.project1.exception.BadRequestException;
import com.example.project1.exception.UnauthorizedAccessException;
import com.example.project1.payload.request.OtpRequest;
import com.example.project1.repository.OtpVerificationRepository;
import com.example.project1.repository.UserRepository;
import com.example.project1.services.OtpVerificationService;
import com.example.project1.utitilies.OtpGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class OtpVerificationServiceImpl implements OtpVerificationService {
    private final OtpVerificationRepository otpVerificationRepository;

    private final UserRepository userRepository;

    private final OtpGenerator otpGenerator;
    @Override
    public OtpVerification createOtpVerification(User user) {
        LocalDateTime expireTime = LocalDateTime.now(ZoneId.systemDefault());
        String otp =  otpGenerator.getRandomOtp();
        OtpVerification otpVerification = new OtpVerification(user, otp,0,expireTime.plusMinutes(1));
        otpVerificationRepository.save(otpVerification);
        return otpVerification;
    }

    @Override
    public boolean verifyOtpVerification(OtpVerification otp) {
        OtpVerification otpVerification = otpVerificationRepository.findByIdAndOtp(otp.getId(), otp.getOtp()).orElseThrow(() -> new BadRequestException("Invalid OTP"));

        if(otpVerification.getExpireTime().isBefore(LocalDateTime.now(ZoneId.systemDefault()))){
            throw new UnauthorizedAccessException("OTP expired time");
        }

        if(otpVerification.getIsUsed() == 1){
            throw new UnauthorizedAccessException("OTP is used");
        }
        return true;
    }

    @Override
    public OtpVerification disableOtpVerification(OtpVerification otp) {
        OtpVerification otpVerification = otpVerificationRepository.findByIdAndOtp(otp.getId(), otp.getOtp()).orElseThrow(() -> new BadRequestException("Invalid OTP"));

        otpVerification.setIsUsed(1);

        otpVerificationRepository.save(otpVerification);

        return otpVerification;
    }

    @Override
    @Transactional
    public void enableUserByOtp(OtpRequest otpRequest) {
        User user = userRepository.findUserByEmail(otpRequest.getEmail()).orElseThrow(() -> new BadRequestException("User not found"));

        OtpVerification otpVerification = otpVerificationRepository.findByUserIdAndOtp(user.getId(), otpRequest.getOtp()).orElseThrow(() -> new BadRequestException("Invalid OTP"));

        verifyOtpVerification(otpVerification);

        user.setStatus(UserStatus.ACTIVE.getValue());

        disableOtpVerification(otpVerification);

        userRepository.save(user);
    }
}
