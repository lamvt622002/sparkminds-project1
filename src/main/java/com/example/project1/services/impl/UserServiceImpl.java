package com.example.project1.services.impl;

import com.example.project1.entities.User;
import com.example.project1.exception.DataBaseAccessException;
import com.example.project1.repository.UserRepository;
import com.example.project1.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User saveUser(User user) {
        User userSaved = userRepository.save(user);

        if(userSaved == null){
            throw new DataBaseAccessException();
        }
        return user;
    }
}
