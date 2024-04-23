package com.example.project1.services;

import com.example.project1.payload.request.*;
import com.example.project1.payload.response.LoginResponse;
import org.springframework.security.core.Authentication;

public interface AuthService {
    void register(RegisterRequest registerRequest);

    LoginResponse login(LoginRequest loginRequest);

    String refreshToken(RefreshTokenRequest request);

    Authentication authenticate(Authentication authentication);

    void resentVerifyRegister(EmailRequest request);

    void resentOtpVerification(EmailRequest request);

    void resetPassword(EmailRequest request);

    void changePassword(ChangePasswordWithoutAuthRequest changePasswordWithoutAuthRequest);

}
