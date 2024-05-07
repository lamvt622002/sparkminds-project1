package com.example.project1.services;

import com.example.project1.payload.request.RefreshTokenRequest;
import com.example.project1.payload.response.JWTPayLoad;

public interface RefreshTokenService {
    String refreshToken(RefreshTokenRequest request);
    String createRefreshToken(JWTPayLoad jwtPayLoad);
}
