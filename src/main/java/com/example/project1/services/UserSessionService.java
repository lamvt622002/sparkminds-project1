package com.example.project1.services;

import com.example.project1.entities.User;
import com.example.project1.entities.UserSession;

import java.util.UUID;

public interface UserSessionService {
    boolean isValidSession(UUID session);

    UserSession createUserSession(User user);

    void disableSession(UUID session);
}
