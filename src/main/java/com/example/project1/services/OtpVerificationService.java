package com.example.project1.services;

import com.example.project1.entities.OtpVerification;
import com.example.project1.entities.User;
import com.example.project1.payload.request.OtpRequest;

public interface OtpVerificationService {
    OtpVerification createOtpVerification(User user);

    boolean verifyOtpVerification(OtpVerification otp);

    OtpVerification disableOtpVerification(OtpVerification otp);

    void enableUserByOtp(OtpRequest otpRequest);

    OtpVerification createOtpChangePhone(User user, String newPhone);

    boolean verifyOtpChangePhone(OtpVerification otpChangePhone);

    OtpVerification disableOtpChangePhone(OtpVerification otpChangePhone);

    void updateUserByOtpChangePhone(OtpVerification otpChangePhone);
}
