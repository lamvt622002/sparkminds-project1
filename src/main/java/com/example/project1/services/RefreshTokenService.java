package com.example.project1.services;

import com.example.project1.entities.RefreshToken;
import com.example.project1.payload.response.JWTPayLoad;

import java.util.Optional;

public interface RefreshTokenService {
    Optional<RefreshToken> findByToken(String token);

    RefreshToken createRefreshToken(JWTPayLoad jwtPayLoad);

    RefreshToken verifyExpiration(RefreshToken refreshToken);

    int deleteByUserId(Long userId);
}
