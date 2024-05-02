package com.example.project1.controllers;

import com.example.project1.payload.dto.JwtResponseDto;
import com.example.project1.payload.request.*;
import com.example.project1.payload.response.LoginResponse;
import com.example.project1.payload.response.RefreshTokenResponse;
import com.example.project1.payload.response.ResponseMessage;
import com.example.project1.payload.response.ResponseRepository;
import com.example.project1.services.AuthService;
import com.example.project1.services.CookiesService;
import com.example.project1.services.OtpVerificationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    private final CookiesService cookiesService;

    private final OtpVerificationService otpVerificationService;

    @PostMapping("/login")
    public ResponseEntity<ResponseRepository> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response){
        String qrCode = authService.login(loginRequest, response);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage<>(true, HttpStatus.OK.value(),qrCode));
    }

    @PostMapping("/two-auth")
    public ResponseEntity<ResponseRepository> twoFactorAuthenticate(@Valid @RequestBody GoogleValidateCodeRequest googleValidateCodeRequest){
        LoginResponse loginResponse = authService.twoFactorAuthenticate(googleValidateCodeRequest);

        HttpHeaders responseCookies  = cookiesService.responseCookies(loginResponse.getEmail());

        return ResponseEntity.status(HttpStatus.OK).headers(responseCookies).body(new ResponseMessage<>(true, HttpStatus.OK.value(),loginResponse));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseRepository> refreshToken(@Valid @RequestBody RefreshTokenRequest request){
        String newAccessToken  = authService.refreshToken(request);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage<>(true, HttpStatus.OK.value(), new RefreshTokenResponse(newAccessToken, request.getRefreshToken())));
    }

    @PostMapping("/resent-verify-link")
    public ResponseEntity<Void> resentVerifyLink(@Valid @RequestBody EmailRequest request){
        authService.resentVerifyLink(request);
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
    @PostMapping("/otp-verification")
    public ResponseEntity<ResponseRepository> verifyOtp(@Valid @RequestBody OtpRequest otpRequest){
        otpVerificationService.enableUserByOtp(otpRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/resent-otp-verification")
    public ResponseEntity<ResponseRepository> resentVerifyOtp(@Valid  @RequestBody EmailRequest emailRequest){
        authService.resentOtpVerification(emailRequest);
        return ResponseEntity.noContent().build();
    }
}
