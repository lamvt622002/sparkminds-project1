package com.example.project1.services.impl;

import com.example.project1.constants.Constants;
import com.example.project1.entities.LinkVerification;
import com.example.project1.entities.User;
import com.example.project1.enums.UserStatus;
import com.example.project1.exception.BadRequestException;
import com.example.project1.exception.DataNotFoundException;
import com.example.project1.payload.response.RegisterResponse;
import com.example.project1.repository.LinkVerificationRepository;
import com.example.project1.repository.UserRepository;
import com.example.project1.services.LinkVerificationService;
import com.example.project1.services.UserSessionService;
import com.example.project1.utitilies.JwtUtilily;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class LinkVerificationServiceImpl implements LinkVerificationService {
    private final LinkVerificationRepository linkVerificationRepository;

    private final JwtUtilily jwtUtilily;

    private final UserRepository userRepository;

    private final UserSessionService userSessionService;

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

        LinkVerification linkVerification = linkVerificationRepository.findByTokenAndEmail(token, email).orElseThrow(() -> new DataNotFoundException(Constants.ERROR_CODE.LINK_VERIFICATION_NOT_FOUND));

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

        theModel.addAttribute("Register", new RegisterResponse(user.getId(),user.getFirstName(), user.getLastName(), user.getBirthDay(), user.getPhoneNumber(), user.getEmail(), user.getFailedLoginAttempts(), user.getLockoutTime(), user.getRole().getAuthority(), user.getStatus(), user.getCreatedAt(), user.getUpdatedAt()));

        return "verify_register.html";
    }

    @Override
    public String verifyLinkChangeEmail(String token, HttpServletRequest request, Model theModel) {
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
        Map<String, Object> claims = jwtUtilily.extractAllClaim(token);
        String newEmail = claims.get("newEmail").toString();

        LinkVerification linkVerification = linkVerificationRepository.findByTokenAndEmail(token, newEmail).orElseThrow(() -> new DataNotFoundException(Constants.ERROR_CODE.LINK_VERIFICATION_NOT_FOUND));

        if(linkVerification.getIsUsed() == 1){
            theModel.addAttribute("Message","Link already used");
            return "verify_error.html";
        }

        User user = userRepository.findUserByEmail(email).orElse(null);

        if(user == null){
            theModel.addAttribute("Message","User not found");
            return "verify_error.html";
        }

        user.setEmail(newEmail);

        userRepository.save(user);

        disableLinkVerification(linkVerification);

        userSessionService.clearAllUserSession(user);

        theModel.addAttribute("Register", new RegisterResponse(user.getId(),user.getFirstName(), user.getLastName(), user.getBirthDay(), user.getPhoneNumber(), user.getEmail(), user.getFailedLoginAttempts(), user.getLockoutTime(), user.getRole().getAuthority(), user.getStatus(), user.getCreatedAt(), user.getUpdatedAt()));

        return "verify_register.html";
    }


    @Override
    public LinkVerification createLinkChangeEmailVerification(User user, String newEmail) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("newEmail", newEmail);

        String token = jwtUtilily.createToken(claims,user.getEmail(), 15);

        LocalDateTime expiredTime = LocalDateTime.now(ZoneId.systemDefault()).plusMinutes(15);

        LinkVerification linkVerification = new LinkVerification(user,newEmail, token, 0, expiredTime);

        linkVerificationRepository.save(linkVerification);

        return linkVerification;
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

        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new BadRequestException(Constants.ERROR_CODE.USER_NOT_FOUND, email));

        user.setStatus(UserStatus.ACTIVE.getValue());
        return user;
    }

    @Override
    public LinkVerification disableLinkVerification(LinkVerification linkVerification) {
        linkVerification.setIsUsed(1);

        linkVerificationRepository.save(linkVerification);

        return linkVerification;
    }

    private String verifyToken(String token, Model theModel) {
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
        return null;
    }

}
