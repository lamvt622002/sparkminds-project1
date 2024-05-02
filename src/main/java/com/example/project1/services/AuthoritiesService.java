package com.example.project1.services;

import com.example.project1.entities.Authorities;

import java.util.Optional;

public interface AuthoritiesService {
    Optional<Authorities> findAuthorityByAuthority(String authority);
}
