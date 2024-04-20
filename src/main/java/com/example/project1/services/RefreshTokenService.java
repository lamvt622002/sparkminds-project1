package com.example.project1.services;

import com.example.project1.entities.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    Optional<RefreshToken> findByToken(String token);

    RefreshToken createRefreshToken(Long userId);

    RefreshToken verifyExpiration(RefreshToken refreshToken);

    int deleteByUserId(Long userId);
}
