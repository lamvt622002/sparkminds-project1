package com.example.project1.services;

import com.example.project1.entities.User;
import com.example.project1.payload.request.ChangeEmailRequest;
import com.example.project1.payload.request.ChangePasswordRequest;
import com.example.project1.payload.request.EmailRequest;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserByEmail(String email);

    User saveUser(User user);

    void changePassword(ChangePasswordRequest changePasswordRequest);

    void changeEmail(ChangeEmailRequest changeEmailRequest);
}
