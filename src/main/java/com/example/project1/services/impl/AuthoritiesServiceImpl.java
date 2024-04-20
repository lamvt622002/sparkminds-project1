package com.example.project1.services.impl;

import com.example.project1.entities.Authorities;
import com.example.project1.repository.AuthoritiesRepository;
import com.example.project1.services.AuthoritiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class AuthoritiesServiceImpl implements AuthoritiesService {
    private final AuthoritiesRepository authoritiesRepository;

    @Override
    public Optional<Authorities> findAuthorityByAuthority(String authority) {
        return authoritiesRepository.findByAuthority(authority);
    }
}
