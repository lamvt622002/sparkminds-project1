package com.example.project1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityFilterchainConfig {
    private final AuthenticationProvider authenticationProvider;

    public SecurityFilterchainConfig(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.cors(c -> c.disable()).csrf(c -> c.disable());
        httpSecurity.authenticationProvider(authenticationProvider);

        httpSecurity.authorizeHttpRequests(c ->
                c.anyRequest().permitAll());

        return httpSecurity.build();
    }
}
