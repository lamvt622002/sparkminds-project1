package com.example.project1.security;

import com.example.project1.constants.Constants;
import com.example.project1.entities.User;
import com.example.project1.enums.UserStatus;
import com.example.project1.exception.DataNotFoundException;
import com.example.project1.services.impl.UserServiceImpl;
import com.example.project1.utitilies.JwtUtilily;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Optional;

@Component
public class AuthCheckUserEnableFilter extends OncePerRequestFilter {
    private final UserServiceImpl userService;
    private final JwtUtilily jwtUtilily;

    private final HandlerExceptionResolver resolver;

    public AuthCheckUserEnableFilter(UserServiceImpl userService,
                                     JwtUtilily jwtUtilily,
                                     @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.userService = userService;
        this.jwtUtilily = jwtUtilily;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String url = request.getRequestURI();

            if(url.startsWith("/api/auth") || url.startsWith("/swagger-ui") || url.startsWith("/v3/api-docs")){
                filterChain.doFilter(request, response);
                return;
            }

            Object email = request.getAttribute("Email");

            Optional<User> getUser = userService.findUserByEmail(((String) email));

            User user = getUser.orElseThrow(() -> new DataNotFoundException(Constants.ERROR_CODE.USER_NOT_FOUND, email));

            if(user.getStatus() != UserStatus.ACTIVE.getValue()){
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return;
            }

            filterChain.doFilter(request, response);
        }catch (Exception e){
            resolver.resolveException(request, response, null, e);
        }
    }
}
