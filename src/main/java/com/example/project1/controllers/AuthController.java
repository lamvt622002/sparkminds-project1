package com.example.project1.controllers;

import com.example.project1.payload.request.*;
import com.example.project1.payload.response.LoginResponse;
import com.example.project1.payload.response.RefreshTokenResponse;
import com.example.project1.payload.response.ResponseMessage;
import com.example.project1.payload.response.ResponseRepository;
import com.example.project1.services.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseRepository> login(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse = authService.login(loginRequest);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage<>(true, HttpStatus.OK.value(),loginResponse));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest registerRequest){
        authService.register(registerRequest);

        return  ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseRepository> refreshToken(@Valid @RequestBody RefreshTokenRequest request){
        String newAccessToken  = authService.refreshToken(request);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage<>(true, HttpStatus.OK.value(), new RefreshTokenResponse(newAccessToken, request.getRefreshToken())));
    }

    @PostMapping("/resent-verify-register")
    public ResponseEntity<Void> resentVerifyRegister(@Valid @RequestBody EmailRequest request){
        authService.resentVerifyRegister(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseRepository> forgotPassword(@Valid @RequestBody EmailRequest request){
        authService.resetPassword(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/change-password")
    public ResponseEntity<ResponseRepository> changePassword(@Valid @RequestBody ChangePasswordWithoutAuthRequest request){
        authService.changePassword(request);
        return ResponseEntity.noContent().build();
    }
}
