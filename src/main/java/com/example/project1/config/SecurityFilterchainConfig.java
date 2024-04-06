package com.example.project1.config;

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

@Configuration
public class SecurityFilterchainConfig {
    private final AuthenticationProvider authenticationProvider;
    private final AuthJwtRequestFilter authJwtRequestFilter;

    public SecurityFilterchainConfig(AuthenticationProvider authenticationProvider, AuthJwtRequestFilter authJwtRequestFilter) {
        this.authenticationProvider = authenticationProvider;
        this.authJwtRequestFilter = authJwtRequestFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.cors(c -> c.disable()).csrf(c -> c.disable());
        httpSecurity.addFilterBefore(authJwtRequestFilter ,UsernamePasswordAuthenticationFilter.class);
        httpSecurity.authenticationProvider(authenticationProvider);
        httpSecurity.authorizeHttpRequests(c -> c.requestMatchers("/api/auth/**").permitAll());
        httpSecurity.authorizeHttpRequests(c -> c.anyRequest().permitAll());

        return httpSecurity.build();
    }
}
