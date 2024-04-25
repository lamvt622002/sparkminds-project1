package com.example.project1.services;

import com.example.project1.entities.LinkVerification;
import com.example.project1.entities.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;

public interface LinkVerificationService {
    String verifyLink(String token, Model theModel);

    String verifyLinkChangeEmail(String token, HttpServletRequest request, Model theModel);

    LinkVerification createLinkVerification(User user);

    LinkVerification createLinkChangeEmailVerification(User user, String newEmail);

    User enableUserByLinkVerification(LinkVerification linkVerification);

    LinkVerification disableLinkVerification(LinkVerification linkVerification);

}
