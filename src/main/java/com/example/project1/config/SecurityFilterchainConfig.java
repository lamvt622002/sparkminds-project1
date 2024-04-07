package com.example.project1.config;

import com.example.project1.security.AuthCheckUserEnableFilter;
import com.example.project1.security.AuthJwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
public class SecurityFilterchainConfig {
    private final AuthenticationProvider authenticationProvider;
    private final AuthJwtRequestFilter authJwtRequestFilter;
    private final AuthCheckUserEnableFilter authCheckUserEnableFilter;

    public SecurityFilterchainConfig(AuthenticationProvider authenticationProvider, AuthJwtRequestFilter authJwtRequestFilter, AuthCheckUserEnableFilter authCheckUserEnableFilter) {
        this.authenticationProvider = authenticationProvider;
        this.authJwtRequestFilter = authJwtRequestFilter;
        this.authCheckUserEnableFilter = authCheckUserEnableFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(c -> c.disable()).csrf(c -> c.disable());
        httpSecurity.addFilterBefore(authJwtRequestFilter ,UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterAfter(authCheckUserEnableFilter, AuthJwtRequestFilter.class);
        httpSecurity.authenticationProvider(authenticationProvider);
        httpSecurity.authorizeHttpRequests(c -> c.requestMatchers("/api/auth/**").permitAll());
        httpSecurity.authorizeHttpRequests(c -> c.anyRequest().authenticated());

        return httpSecurity.build();
    }
}
