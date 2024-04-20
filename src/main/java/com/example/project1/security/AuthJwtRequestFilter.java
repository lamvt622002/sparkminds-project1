package com.example.project1.security;

import com.example.project1.repository.UserRepository;
import com.example.project1.utitilies.JwtUtilily;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class AuthJwtRequestFilter extends OncePerRequestFilter {
    private final JwtUtilily jwtUtilily;
    private final UserDetailsServiceSecurity userDetailsServiceSecurity;

    public AuthJwtRequestFilter(JwtUtilily jwtUtilily, UserDetailsServiceSecurity userDetailsServiceSecurity) {
        this.jwtUtilily = jwtUtilily;
        this.userDetailsServiceSecurity = userDetailsServiceSecurity;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURI();

        if(url.startsWith("/api/auth")|| url.startsWith("/swagger-ui") ){
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println(request.getHeader("Authorization"));

        String email = null;
        String token = null;

        String authorization = request.getHeader("Authorization");

        if(authorization != null){
            token = authorization;
            email = jwtUtilily.extractUserName(token);
        }


        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
            request.setAttribute("Email", email);

            UserDetails userDetails = userDetailsServiceSecurity.loadUserByUsername(email);
            if(jwtUtilily.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
