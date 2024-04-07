package com.example.project1.security;

import com.example.project1.entities.User;
import com.example.project1.exception.DataNotFoundExeption;
import com.example.project1.services.impl.UserServiceImpl;
import com.example.project1.utitilies.JwtUtilily;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class AuthCheckUserEnableFilter extends OncePerRequestFilter {
    private final UserServiceImpl userService;
    private final JwtUtilily jwtUtilily;

    public AuthCheckUserEnableFilter(UserServiceImpl userService, JwtUtilily jwtUtilily) {
        this.userService = userService;
        this.jwtUtilily = jwtUtilily;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURI();

        if(url.startsWith("/api/auth")){
            filterChain.doFilter(request, response);
            return;
        }

        Object email = request.getAttribute("Email");
        System.out.println("Email " + email);

        Optional<User> getUser = userService.findUserByEmail(((String) email));

        User user = getUser.orElseThrow(() -> new DataNotFoundExeption("User not found"));

        if(user.getEnabled() == 0){
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
