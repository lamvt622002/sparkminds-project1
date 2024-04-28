package com.example.project1.services.impl;

import com.example.project1.constants.Constants;
import com.example.project1.entities.RefreshToken;
import com.example.project1.entities.User;
import com.example.project1.exception.DataNotFoundException;
import com.example.project1.exception.UnauthorizedAccessException;
import com.example.project1.payload.response.JWTPayLoad;
import com.example.project1.repository.RefreshTokenRepository;
import com.example.project1.repository.UserRepository;
import com.example.project1.services.RefreshTokenService;
import com.example.project1.utitilies.JwtUtilily;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    private final JwtUtilily jwtUtilily;
    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken createRefreshToken(JWTPayLoad jwtPayLoad) {
        RefreshToken refreshToken = new RefreshToken();

        User user = userRepository.findById(jwtPayLoad.getUserID()).orElseThrow(() -> new DataNotFoundException(Constants.ERROR_CODE.USER_NOT_FOUND));

        String token = jwtUtilily.generateToken(jwtPayLoad, 60);

        refreshToken.setUser(user);
        refreshToken.setExpireTime(LocalDateTime.now(ZoneId.systemDefault()).plusMinutes(60));
        refreshToken.setToken(token);


        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        try{
            jwtUtilily.isTokenExpired(refreshToken.getToken());
        }catch(ExpiredJwtException ex){
            refreshTokenRepository.delete(refreshToken);
            throw new UnauthorizedAccessException(Constants.ERROR_CODE.EXPIRED_SESSION);
        }
        return refreshToken;
    }

    @Override
    public int deleteByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException(Constants.ERROR_CODE.USER_NOT_FOUND));
        return refreshTokenRepository.deleteByUser(user);
    }
}
