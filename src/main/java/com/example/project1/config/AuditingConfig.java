package com.example.project1.config;

import com.example.project1.entities.User;
import com.example.project1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef="auditorProvider")
@RequiredArgsConstructor
public class AuditingConfig{
    private final UserRepository userRepository;
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            if(SecurityContextHolder.getContext().getAuthentication() != null){
                String username = SecurityContextHolder.getContext().getAuthentication().getName();
                return Optional.ofNullable(username);
            }
            return Optional.of("anonymousUser");
        };
    }
}
