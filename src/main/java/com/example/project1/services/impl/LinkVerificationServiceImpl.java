package com.example.project1.services.impl;

import com.example.project1.entities.LinkVerification;
import com.example.project1.entities.User;
import com.example.project1.enums.UserStatus;
import com.example.project1.exception.BadRequestException;
import com.example.project1.exception.DataNotFoundExeption;
import com.example.project1.payload.response.RegisterResponse;
import com.example.project1.repository.LinkVerificationRepository;
import com.example.project1.repository.UserRepository;
import com.example.project1.services.LinkVerificationService;
import com.example.project1.services.UserService;
import com.example.project1.utitilies.JwtUtilily;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@Transactional
@RequiredArgsConstructor
public class LinkVerificationServiceImpl implements LinkVerificationService {
    private final LinkVerificationRepository linkVerificationRepository;

    private final JwtUtilily jwtUtilily;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public String verifyLink(String token, Model theModel) {
        if(token.isBlank()){
            theModel.addAttribute("Message","Invalid token");
            return "verify_error.html";
        }

        try{
            jwtUtilily.isTokenExpired(token);
        }catch (ExpiredJwtException ex){
            theModel.addAttribute("Message","Token is expired");
            return "verify_error.html";
        }

        String email = jwtUtilily.extractUserName(token);

        LinkVerification linkVerification = linkVerificationRepository.findByTokenAndEmail(token, email).orElseThrow(() -> new DataNotFoundExeption("User not found"));

        if(linkVerification.getIsUsed() == 1){
            theModel.addAttribute("Message","Link already used");
            return "verify_error.html";
        }

        User user = userRepository.findUserByEmail(email).orElse(null);

        if(user == null){
            theModel.addAttribute("Message","User not found");
            return "verify_error.html";
        }

        disableLinkVerification(linkVerification);

        enableUserByLinkVerification(linkVerification);

        theModel.addAttribute("Register", new RegisterResponse(user.getId(),user.getFirstName(), user.getLastName(), user.getBirthDay(), user.getPhoneNumber(), user.getEmail(), user.getFailedLoginAttempts(), user.getLockoutTime(), user.getRole().getAuthority(), user.getStatus(), user.getIsDelete(), user.getCreatedAt(), user.getUpdatedAt()));

        return "verify_register.html";
    }

    @Override
    public LinkVerification createLinkVerification(User user) {
        String token = jwtUtilily.generateTokenForEmail(user.getEmail(), 15);

        LocalDateTime expiredTime = LocalDateTime.now(ZoneId.systemDefault()).plusMinutes(15);

        LinkVerification linkVerification = new LinkVerification(user,user.getEmail(), token, 0, expiredTime);

        linkVerificationRepository.save(linkVerification);

        return linkVerification;
    }

    @Override
    public User enableUserByLinkVerification(LinkVerification linkVerification) {
        String email = jwtUtilily.extractUserName(linkVerification.getToken());

        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new BadRequestException("User not found"));

        user.setStatus(UserStatus.ACTIVE.getValue());
        return user;
    }

    @Override
    public LinkVerification disableLinkVerification(LinkVerification linkVerification) {
        linkVerification.setIsUsed(1);

        linkVerificationRepository.save(linkVerification);

        return linkVerification;
    }

}
