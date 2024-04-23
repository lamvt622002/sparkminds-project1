package com.example.project1.services.impl;

import com.example.project1.entities.EmailVerification;
import com.example.project1.entities.OtpVerification;
import com.example.project1.entities.User;
import com.example.project1.repository.EmailVerificationRepository;
import com.example.project1.services.OtpVerificationService;
import com.example.project1.services.SendingEmailService;
import com.example.project1.services.EmailVerificationService;
import com.example.project1.utitilies.JwtUtilily;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class SendingEmailServiceImpl implements SendingEmailService {
    private final JavaMailSender emailSender;

    private final JwtUtilily jwtUtilily;

    private final EmailVerificationRepository emailVerificationRepository;

    private final OtpVerificationService otpVerificationService;

    private final SimpleMailMessage simpleMailMessage;

    @Value("${application.apiVerifyEmail}")
    private String apiVerifyEmail;

    @Override
    public void sendMessage(String to, String subject, String text) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("uitcarshop@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        emailSender.send(simpleMailMessage);
    }

    @Override
    public void sendVerificationEmail(User user) {
        String token = jwtUtilily.generateTokenForEmail(user.getEmail(), 15);

        LocalDateTime expiredTime = LocalDateTime.now(ZoneId.systemDefault()).plusHours(2);

        EmailVerification emailVerification = new EmailVerification(user,user.getEmail(), token, 0, expiredTime);

        emailVerificationRepository.save(emailVerification);

        String text = String.format(simpleMailMessage.getText(), apiVerifyEmail + token);

        sendMessage(user.getEmail(), "Please verify your email for your account", text);
    }

    @Override
    public void sendResetPassword(User user, String password) {
        String text = String.format(
                """
                        Hi. %s\s
                        You are recently requested to reset your password\s
                        Here is your new password\s

                        %s\s
                        
                        Please change new password after login again\s
                       
                        lamvt - Sparkminds""", user.getFirstName() + user.getLastName(),password );
        sendMessage(user.getEmail(), "Sparkminds-Project1: Reset password", text);
    }

    @Override
    public void sentOtpVerification(User user) {
        OtpVerification otpVerification = otpVerificationService.createOtpVerification(user);
        String text = String.format(
                """
                        Hi. %s\s
                        Verify your email address\s
                        You are recently signed up for your account\s
                        Please enter the following verification code
                        %s\s
                        lamvt - Sparkminds""", otpVerification.getUser().getFirstName() + otpVerification.getUser().getLastName(), otpVerification.getOtp() );
        sendMessage(user.getEmail(), "Sparkminds-Project1: Email verification", text);
    }


}
