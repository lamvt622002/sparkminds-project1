package com.example.project1.security;

import com.example.project1.constants.Constants;
import com.example.project1.exception.BadRequestException;
import com.example.project1.repository.UserSessionRepository;
import com.example.project1.services.UserSessionService;
import com.example.project1.utitilies.JwtUtilily;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LogoutHandlerSecurity implements LogoutHandler {
    private final JwtUtilily jwtUtilily;

    private final UserSessionService userSessionService;

    @Override
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader("Authorization");
        System.out.println(token);

        if(token == null){
            throw new BadRequestException(Constants.ERROR_CODE.INVALID_TOKEN);
        }

        Map<String, Object> claims = jwtUtilily.extractAllClaim(token);

        System.out.println(claims);

        System.out.println(claims.get("session").toString());

        UUID session = UUID.fromString(claims.get("session").toString());

        userSessionService.disableSession(session);
    }
}
