package com.example.project1.controllers;

import com.example.project1.entities.OtpChangePhone;
import com.example.project1.payload.request.ChangeEmailRequest;
import com.example.project1.payload.request.ChangePasswordRequest;
import com.example.project1.payload.request.ChangePhoneRequest;
import com.example.project1.payload.request.OtpChangePhoneRequest;
import com.example.project1.payload.response.ResponseRepository;
import com.example.project1.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/change-password")
    public ResponseEntity<ResponseRepository> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest){
        userService.changePassword(changePasswordRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/change-email")
    public ResponseEntity<ResponseRepository> changeEmail(@Valid @RequestBody ChangeEmailRequest changeEmailRequest){
        userService.changeEmail(changeEmailRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/change-phone")
    public ResponseEntity<ResponseRepository> changePhone(@Valid @RequestBody ChangePhoneRequest changePhoneRequest){
        userService.changePhone(changePhoneRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/change-phone-verify")
    public ResponseEntity<ResponseRepository> changePhoneVerify(@Valid @RequestBody OtpChangePhoneRequest otpChangePhone){
        userService.verifyChangePhone(otpChangePhone);
        return ResponseEntity.noContent().build();
    }
}
