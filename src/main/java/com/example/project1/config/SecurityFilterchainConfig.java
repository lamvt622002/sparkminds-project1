package com.example.project1.config;

import com.example.project1.security.AuthCheckUserEnableFilter;
import com.example.project1.security.AuthJwtRequestFilter;
import com.example.project1.security.LogoutHandlerSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityFilterchainConfig {
    private final AuthenticationProvider authenticationProvider;
    private final AuthJwtRequestFilter authJwtRequestFilter;
    private final AuthCheckUserEnableFilter authCheckUserEnableFilter;
    private final LogoutHandlerSecurity logoutHandlerSecurity;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(Customizer.withDefaults());
        httpSecurity.csrf(c -> c.disable());
        httpSecurity.addFilterBefore(authJwtRequestFilter ,UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterAfter(authCheckUserEnableFilter, AuthJwtRequestFilter.class);
        httpSecurity.authenticationProvider(authenticationProvider);
        httpSecurity.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.logout(c -> c.addLogoutHandler(logoutHandlerSecurity).logoutUrl("/api/user/logout").permitAll());
        httpSecurity.authorizeHttpRequests(c -> c.requestMatchers(
                "/api/auth/**",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/v2/api-docs",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**").permitAll());
        httpSecurity.authorizeHttpRequests(c -> c.anyRequest().authenticated());

        return httpSecurity.build();
    }
}
