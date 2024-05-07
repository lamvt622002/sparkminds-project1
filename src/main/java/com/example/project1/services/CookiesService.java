package com.example.project1.services;

import com.example.project1.payload.request.RefreshTokenRequest;
import org.springframework.http.HttpHeaders;

public interface CookiesService {
    HttpHeaders responseCookies(String email);

    HttpHeaders refreshTokenResponseCookies(RefreshTokenRequest request);
}
