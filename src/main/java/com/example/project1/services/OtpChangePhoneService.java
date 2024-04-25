package com.example.project1.services;

import com.example.project1.entities.OtpChangePhone;
import com.example.project1.entities.User;

public interface OtpChangePhoneService {
    OtpChangePhone createOtpChangePhone(User user, String newPhone);

    boolean verifyOtpChangePhone(OtpChangePhone otpChangePhone);

    OtpChangePhone disableOtpChangePhone(OtpChangePhone otpChangePhone);

    void updateUserByOtpChangePhone(OtpChangePhone otpChangePhone);
}
