package com.example.project1.services.impl;

import com.example.project1.entities.User;
import com.example.project1.enums.UserStatus;
import com.example.project1.exception.BadRequestException;
import com.example.project1.exception.DataNotFoundExeption;
import com.example.project1.exception.DatabaseAccessException;
import com.example.project1.payload.request.ChangeEmailRequest;
import com.example.project1.payload.request.ChangePasswordRequest;
import com.example.project1.payload.request.EmailRequest;
import com.example.project1.repository.UserRepository;
import com.example.project1.services.SendingEmailService;
import com.example.project1.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final SendingEmailService sendingEmailService;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserByEmail(String email) {
        Optional<User> getUser = userRepository.findUserByEmail(email);
        return getUser;
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
    public void enableUser(User user) {
        user.setStatus(UserStatus.ACTIVE.getValue());
        userRepository.save(user);
    }
    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new DataNotFoundExeption("User not found"));

        if(user.getStatus() != UserStatus.ACTIVE.getValue()){
            throw new BadRequestException("Your account is not active");
        }

        if(!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())){
            throw new BadRequestException("Incorrect password");
        }

        if(passwordEncoder.matches(changePasswordRequest.getNewPassword(), user.getPassword())){
            throw new BadRequestException("The new password and the old one must be different");
        }

        String encodeNewPassword = passwordEncoder.encode(changePasswordRequest.getNewPassword());

        user.setPassword(encodeNewPassword);

        userRepository.save(user);
    }

    @Override
    public void changeEmail(ChangeEmailRequest changeEmailRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new DataNotFoundExeption("User not found"));

        if(!passwordEncoder.matches(changeEmailRequest.getPassword(),user.getPassword())){
            throw new BadRequestException("Invalid password");
        }

        if(userRepository.existsByEmail(changeEmailRequest.getNewEmail())){
            throw new BadRequestException("Email already exists");
        }

        user.setEmail(changeEmailRequest.getNewEmail());

        user.setStatus(UserStatus.INACTIVE.getValue());

        userRepository.save(user);

        sendingEmailService.sendVerificationEmail(user);



    }
}
