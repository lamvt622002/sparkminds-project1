package com.example.project1.services.impl;

import com.example.project1.constants.Constants;
import com.example.project1.entities.OtpChangePhone;
import com.example.project1.entities.User;
import com.example.project1.enums.UserStatus;
import com.example.project1.exception.BadRequestException;
import com.example.project1.exception.DataNotFoundException;
import com.example.project1.exception.DatabaseAccessException;
import com.example.project1.exception.UnauthorizedAccessException;
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

        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new DataNotFoundException(Constants.ERROR_CODE.USER_NOT_FOUND, email));

        if(user.getStatus() != UserStatus.ACTIVE.getValue()){
            throw new UnauthorizedAccessException(Constants.ERROR_CODE.NOT_ACTIVE_ACCOUNT);
        }

        if(!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())){
            throw new BadRequestException(Constants.ERROR_CODE.INVALID_PASSWORD);
        }

        if(passwordEncoder.matches(changePasswordRequest.getNewPassword(), user.getPassword())){
            throw new BadRequestException(Constants.ERROR_CODE.DUPLICATE_OLD_PASSWORD_NEW_PASSWORD);
        }

        if(!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmNewPassword())){
            throw new BadRequestException(Constants.ERROR_CODE.INVALID_PASSWORD_AND_CONFIRM_PASSWORD);
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

        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new DataNotFoundException(Constants.ERROR_CODE.USER_NOT_FOUND, email));

        if(!passwordEncoder.matches(changeEmailRequest.getPassword(),user.getPassword())){
            throw new UnauthorizedAccessException(Constants.ERROR_CODE.INVALID_PASSWORD);
        }

        if(userRepository.existsByEmail(changeEmailRequest.getNewEmail())){
            throw new BadRequestException(Constants.ERROR_CODE.EMAIL_ALREADY_EXISTS);
        }

        if(user.getEmail().equals(changeEmailRequest.getNewEmail())){
            throw new BadRequestException(Constants.ERROR_CODE.DUPLICATE_OLD_EMAIL_AND_NEW_EMAIL);
        }

        sendingEmailService.sendVerificationChangeEmail(user, changeEmailRequest.getNewEmail());
    }

    @Override
    public void changePhone(ChangePhoneRequest changePhoneRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new DataNotFoundException(Constants.ERROR_CODE.USER_NOT_FOUND, email));

        if(!passwordEncoder.matches(changePhoneRequest.getPassword(),user.getPassword())){
            throw new BadRequestException(Constants.ERROR_CODE.INVALID_PASSWORD);
        }

        if(userRepository.existsByPhoneNumber(changePhoneRequest.getPhoneNumber())){
            throw new BadRequestException(Constants.ERROR_CODE.PHONE_NUMBER_ALREADY_EXISTS);
        }

        if(user.getPhoneNumber().equals(changePhoneRequest.getPhoneNumber())){
            throw new BadRequestException(Constants.ERROR_CODE.DUPLICATE_OLD_PHONE_AND_NEW_PHONE);
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

        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new DataNotFoundException(Constants.ERROR_CODE.USER_NOT_FOUND, email));

        OtpChangePhone otpChangePhone = otpChangePhoneRepository.findByUserIdAndOtp(user.getId(), verifyChangePhoneRequest.getOtp()).orElseThrow(() -> new DataNotFoundException(Constants.ERROR_CODE.INVALID_CODE, verifyChangePhoneRequest.getOtp()));

         phoneService.verifyOtpChangePhone(otpChangePhone);

         phoneService.disableOtpChangePhone(otpChangePhone);

         phoneService.updateUserByOtpChangePhone(otpChangePhone);
    }
}
