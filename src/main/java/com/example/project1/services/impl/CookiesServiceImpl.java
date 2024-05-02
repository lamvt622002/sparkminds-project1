package com.example.project1.services.impl;

import com.example.project1.constants.Constants;
import com.example.project1.entities.RefreshToken;
import com.example.project1.entities.User;
import com.example.project1.entities.UserSession;
import com.example.project1.exception.DataNotFoundException;
import com.example.project1.payload.response.JWTPayLoad;
import com.example.project1.repository.UserRepository;
import com.example.project1.services.CookiesService;
import com.example.project1.services.RefreshTokenService;
import com.example.project1.services.UserSessionService;
import com.example.project1.utitilies.JwtUtilily;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CookiesServiceImpl implements CookiesService {
    private final UserRepository userRepository;
    private final UserSessionService userSessionService;
    private final JwtUtilily jwtUtility;
    private final RefreshTokenService refreshTokenService;
    @Override
    public HttpHeaders responseCookies(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new DataNotFoundException(Constants.ERROR_CODE.USER_NOT_FOUND, email));

        final UserSession userSession = userSessionService.createUserSession(user);

        JWTPayLoad jwtPayLoad = new JWTPayLoad(user.getId(), user.getEmail(), userSession.getSessionId().toString());

        final String accessToken = jwtUtility.generateToken(jwtPayLoad, 30);

        final RefreshToken refreshToken = refreshTokenService.createRefreshToken(jwtPayLoad);

        ResponseCookie access_Token = ResponseCookie.from("access_token", accessToken)
                .httpOnly(false)
                .secure(true)
                .path("/")
                .maxAge(2* 24 * 60 * 60)
                .domain("localhost")
                .build();
        ResponseCookie refresh_Token = ResponseCookie.from("refresh_token", refreshToken.getToken())
                .httpOnly(false)
                .secure(true)
                .path("/")
                .maxAge(2* 24 * 60 * 60)
                .domain("localhost")
                .build();
        ResponseCookie session = ResponseCookie.from("session", userSession.getSessionId().toString())
                .httpOnly(false)
                .secure(true)
                .path("/")
                .maxAge(2* 24 * 60 * 60)
                .domain("localhost")
                .build();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, access_Token.toString());
        responseHeaders.add(HttpHeaders.SET_COOKIE, refresh_Token.toString());
        responseHeaders.add(HttpHeaders.SET_COOKIE, session.toString());
        return responseHeaders;
    }
}
