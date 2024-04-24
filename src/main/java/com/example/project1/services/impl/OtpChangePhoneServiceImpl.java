package com.example.project1.services.impl;

import com.example.project1.entities.OtpChangePhone;
import com.example.project1.entities.OtpVerification;
import com.example.project1.entities.User;
import com.example.project1.exception.BadRequestException;
import com.example.project1.exception.UnauthorizedAccessException;
import com.example.project1.repository.OtpChangePhoneRepository;
import com.example.project1.repository.UserRepository;
import com.example.project1.services.OtpChangePhoneService;
import com.example.project1.utitilies.OtpGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class OtpChangePhoneServiceImpl implements OtpChangePhoneService {
    private final OtpChangePhoneRepository otpChangePhoneRepository;

    private final OtpGenerator otpGenerator;

    private final UserRepository userRepository;
    @Override
    public OtpChangePhone createOtpChangePhone(User user, String newPhone) {
        String otp  = otpGenerator.getRandomOtp();
        LocalDateTime expireTime = LocalDateTime.now(ZoneId.systemDefault()).plusMinutes(1);
        OtpChangePhone otpChangePhone = new OtpChangePhone(user, newPhone, otp, 0,expireTime);
        otpChangePhoneRepository.save(otpChangePhone);
        return otpChangePhone;
    }

    @Override
    public boolean verifyOtpChangePhone(OtpChangePhone otp) {
        if(otp.getExpireTime().isBefore(LocalDateTime.now(ZoneId.systemDefault()))){
            throw new UnauthorizedAccessException("OTP expired time");
        }

        if(otp.getIsUsed() == 1){
            throw new UnauthorizedAccessException("OTP is used");
        }
        return true;
    }

    @Override
    public OtpChangePhone disableOtpChangePhone(OtpChangePhone otpChangePhone) {
        otpChangePhone.setIsUsed(1);

        otpChangePhoneRepository.save(otpChangePhone);
        return otpChangePhone;
    }

    @Override
    public void updateUserByOtpChangePhone(OtpChangePhone otpChangePhone) {
        User user = otpChangePhone.getUser();
        user.setPhoneNumber(otpChangePhone.getNewPhoneNumber());
        userRepository.save(user);
    }
}
