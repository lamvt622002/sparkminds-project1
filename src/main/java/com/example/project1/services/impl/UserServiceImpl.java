package com.example.project1.services.impl;

import com.example.project1.entities.OtpChangePhone;
import com.example.project1.entities.User;
import com.example.project1.enums.UserStatus;
import com.example.project1.exception.BadRequestException;
import com.example.project1.exception.DataNotFoundExeption;
import com.example.project1.exception.DatabaseAccessException;
import com.example.project1.payload.request.*;
import com.example.project1.repository.OtpChangePhoneRepository;
import com.example.project1.repository.UserRepository;
import com.example.project1.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final SendingEmailService sendingEmailService;

    private final SMSService smsService;

    private final OtpVerificationService otpVerificationService;

    private final OtpChangePhoneService phoneService;

    private final OtpChangePhoneRepository otpChangePhoneRepository;

    private final UserSessionService userSessionService;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserByEmail(String email) {
        Optional<User> getUser = userRepository.findUserByEmail(email);
        return getUser;
    }

    @Override
    public User saveUser(User user) {
        User userSaved = userRepository.save(user);

        if(userSaved == null){
            throw new DatabaseAccessException();
        }
        return user;
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new DataNotFoundExeption("User not found"));

        if(user.getStatus() != UserStatus.ACTIVE.getValue()){
            throw new BadRequestException("Your account is not active");
        }

        if(!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())){
            throw new BadRequestException("Incorrect password");
        }

        if(passwordEncoder.matches(changePasswordRequest.getNewPassword(), user.getPassword())){
            throw new BadRequestException("The new password and the old one must be different");
        }

        String encodeNewPassword = passwordEncoder.encode(changePasswordRequest.getNewPassword());

        user.setPassword(encodeNewPassword);

        userRepository.save(user);

        userSessionService.clearAllUserSession(user);
    }

    @Override
    @Transactional
    public void changeEmail(ChangeEmailRequest changeEmailRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new DataNotFoundExeption("User not found"));

        if(!passwordEncoder.matches(changeEmailRequest.getPassword(),user.getPassword())){
            throw new BadRequestException("Invalid password");
        }

        if(userRepository.existsByEmail(changeEmailRequest.getNewEmail())){
            throw new BadRequestException("Email already exists");
        }

        if(user.getEmail().equals(changeEmailRequest.getNewEmail())){
            throw new BadRequestException("New email must be different");
        }

        sendingEmailService.sendVerificationChangeEmail(user, changeEmailRequest.getNewEmail());
    }

    @Override
    public void changePhone(ChangePhoneRequest changePhoneRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new DataNotFoundExeption("User not found"));

        if(!passwordEncoder.matches(changePhoneRequest.getPassword(),user.getPassword())){
            throw new BadRequestException("Invalid password");
        }

        if(userRepository.existsByPhoneNumber(changePhoneRequest.getPhoneNumber())){
            throw new BadRequestException("Phone number already exists");
        }

        if(user.getPhoneNumber().equals(changePhoneRequest.getPhoneNumber())){
            throw new BadRequestException("Phone number must be different");
        }

        OtpChangePhone phone = phoneService.createOtpChangePhone(user, changePhoneRequest.getPhoneNumber());

        String body = String.format(
        """
        Your verification code is:%s\s
        """, phone.getOtp());

        smsService.sendMessage(phone.getNewPhoneNumber(),body);

    }

    @Override
    @Transactional
    public void verifyChangePhone(OtpChangePhoneRequest verifyChangePhoneRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new DataNotFoundExeption("User not found"));

        OtpChangePhone otpChangePhone = otpChangePhoneRepository.findByUserIdAndOtp(user.getId(), verifyChangePhoneRequest.getOtp()).orElseThrow(() -> new DataNotFoundExeption("Invalid OTP"));

         phoneService.verifyOtpChangePhone(otpChangePhone);

         phoneService.disableOtpChangePhone(otpChangePhone);

         phoneService.updateUserByOtpChangePhone(otpChangePhone);
    }
}
