package com.example.project1.services.impl;

import com.example.project1.entities.User;
import com.example.project1.exception.DatabaseAccessException;
import com.example.project1.exception.DataNotFoundExeption;
import com.example.project1.repository.UserRepository;
import com.example.project1.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        Optional<User> getUser = userRepository.findUserByEmail(email);
        User existsUser = getUser.orElseThrow(() -> new DataNotFoundExeption("User not found"));
        return existsUser;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User saveUser(User user) {
        User userSaved = userRepository.save(user);

        if(userSaved == null){
            throw new DatabaseAccessException();
        }
        return user;
    }

    @Override
    public User enableUser(User user) {
        user.setEnabled(1);

        User updateUser = userRepository.save(user);

        return updateUser;
    }
}
