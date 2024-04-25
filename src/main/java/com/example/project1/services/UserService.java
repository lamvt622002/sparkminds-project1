package com.example.project1.services;

import com.example.project1.entities.User;
import com.example.project1.payload.request.*;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserByEmail(String email);

    User saveUser(User user);

    void changePassword(ChangePasswordRequest changePasswordRequest);

    void changeEmail(ChangeEmailRequest changeEmailRequest);

    void changePhone(ChangePhoneRequest changePhoneRequest);

    void verifyChangePhone(OtpChangePhoneRequest verifyChangePhoneRequest);
}
