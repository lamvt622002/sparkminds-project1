package com.example.project1.services;

import com.example.project1.payload.dto.JwtResponseDto;
import com.example.project1.payload.request.*;
import com.example.project1.payload.response.LoginResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;

public interface AuthService {
    void register(RegisterRequest registerRequest);

    String login(LoginRequest loginRequest, HttpServletResponse response);

    LoginResponse twoFactorAuthenticate(GoogleValidateCodeRequest googleValidateCodeRequest);

    HttpHeaders responseCookies(String email);

    String refreshToken(RefreshTokenRequest request);

    Authentication authenticate(Authentication authentication);

    void resentVerifyLink(EmailRequest request);

    void resentOtpVerification(EmailRequest request);

    void resetPassword(EmailRequest request);

    void changePassword(ChangePasswordWithoutAuthRequest changePasswordWithoutAuthRequest);

}
