package com.example.project1.services;

import com.example.project1.entities.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    User saveUser(User user);
}
