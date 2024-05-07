package com.example.project1.services.impl;

import com.example.project1.constants.Constants;
import com.example.project1.entities.User;
import com.example.project1.entities.UserSession;
import com.example.project1.exception.DataNotFoundException;
import com.example.project1.exception.InvalidSessionException;
import com.example.project1.payload.request.RefreshTokenRequest;
import com.example.project1.payload.response.JWTPayLoad;
import com.example.project1.repository.UserRepository;
import com.example.project1.services.RefreshTokenService;
import com.example.project1.services.UserSessionService;
import com.example.project1.utitilies.JwtUtilily;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final JwtUtilily jwtUtility;

    private final UserSessionService userSessionService;

    private final UserRepository userRepository;
    @Override
    public String refreshToken(RefreshTokenRequest request) {
        String token = request.getRefreshToken();
        String email = null;
        Map<String, Object> claims;

        try{
            email = jwtUtility.extractUserName(token);
        }catch (ExpiredJwtException ex){
            throw new InvalidSessionException(Constants.ERROR_CODE.EXPIRED_SESSION);
        }

        claims = jwtUtility.extractAllClaim(token);

        UUID sessionId = UUID.fromString(claims.get("session").toString());

        if(!userSessionService.isValidSession(sessionId)){
            throw new InvalidSessionException(Constants.ERROR_CODE.INVALID_SESSION);
        }

        String finalEmail = email;
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new DataNotFoundException(Constants.ERROR_CODE.USER_NOT_FOUND, finalEmail));

        final UserSession userSession = userSessionService.createUserSession(user);

        JWTPayLoad  jwtPayLoad = new JWTPayLoad(user.getId(), user.getEmail(), userSession.getSessionId().toString());

        String newAccessToken = jwtUtility.generateToken(jwtPayLoad, 60);

        return newAccessToken;
    }

    @Override
    public String createRefreshToken(JWTPayLoad jwtPayLoad) {
        return  jwtUtility.generateToken(jwtPayLoad, 60);
    }
}
