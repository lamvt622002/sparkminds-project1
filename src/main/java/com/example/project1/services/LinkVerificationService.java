package com.example.project1.services;

import com.example.project1.entities.LinkVerification;
import com.example.project1.entities.User;
import org.springframework.ui.Model;

public interface LinkVerificationService {
    String verifyLink(String token, Model theModel);

    LinkVerification createLinkVerification(User user);

    User enableUserByLinkVerification(LinkVerification linkVerification);

    LinkVerification disableLinkVerification(LinkVerification linkVerification);

}
