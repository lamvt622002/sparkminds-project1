package com.example.project1.services.impl;

import com.example.project1.entities.RefreshToken;
import com.example.project1.entities.User;
import com.example.project1.exception.DataNotFoundExeption;
import com.example.project1.exception.VerifyException;
import com.example.project1.payload.response.JWTPayLoad;
import com.example.project1.repository.RefreshTokenRepository;
import com.example.project1.repository.UserRepository;
import com.example.project1.services.RefreshTokenService;
import com.example.project1.services.UserService;
import com.example.project1.utitilies.JwtUtilily;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
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

        User user = userRepository.findById(jwtPayLoad.getUserID()).orElseThrow(() -> new DataNotFoundExeption("User not found"));

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
            throw new VerifyException("Your session have been expired. Please login again");
        }
        return refreshToken;
    }

    @Override
    public int deleteByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundExeption("User not found"));
        return refreshTokenRepository.deleteByUser(user);
    }
}
