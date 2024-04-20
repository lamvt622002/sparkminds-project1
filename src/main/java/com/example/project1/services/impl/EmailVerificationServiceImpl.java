package com.example.project1.services.impl;

import com.example.project1.entities.EmailVerification;
import com.example.project1.entities.User;
import com.example.project1.exception.DatabaseAccessException;
import com.example.project1.exception.DataNotFoundExeption;
import com.example.project1.payload.response.RegisterResponse;
import com.example.project1.repository.EmailVerificationRepository;
import com.example.project1.repository.UserRepository;
import com.example.project1.services.EmailVerificationService;
import com.example.project1.services.UserService;
import com.example.project1.utitilies.JwtUtilily;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailVerificationServiceImpl implements EmailVerificationService {
    private final EmailVerificationRepository emailVerificationRepository;

    private final UserService userService;

    private final JwtUtilily jwtUtilily;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public String verifyEmail(String token, Model theModel) {
        if(token.isBlank()){
            theModel.addAttribute("Message","Invalid token");
            return "verify_error.html";
        }

        boolean isTokenExpire;

        try{
            isTokenExpire = jwtUtilily.isTokenExpired(token);
        }catch (ExpiredJwtException ex){
            theModel.addAttribute("Message","Token is expired");
            return "verify_error.html";
        }

        String email = jwtUtilily.extractUserName(token);

        EmailVerification emailVerification = emailVerificationRepository.findByTokenAndEmail(token, email).orElseThrow(() -> new DataNotFoundExeption("Data not found"));

        if(emailVerification.getIsUsed() == 1){
            theModel.addAttribute("Message","Link already used");
            return "verify_error.html";
        }

        User user = userRepository.findUserByEmail(email).orElse(null);

        if(user == null){
            theModel.addAttribute("Message","User not found");
            return "verify_error.html";
        }

        emailVerification.setIsUsed(1);

        emailVerificationRepository.save(emailVerification);

        userService.enableUser(user);

        theModel.addAttribute("Register", new RegisterResponse(user.getId(),user.getFirstName(), user.getLastName(), user.getBirthDay(), user.getPhoneNumber(), user.getEmail(), user.getFailedLoginAttempts(), user.getLockoutTime(), user.getRole().getAuthority(), user.getStatus(), user.getIsDelete(), user.getCreatedAt(), user.getUpdatedAt()));

        return "verify_register.html";
    }

}
