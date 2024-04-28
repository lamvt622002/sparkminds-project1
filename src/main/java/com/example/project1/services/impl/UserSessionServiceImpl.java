package com.example.project1.services.impl;

import com.example.project1.constants.Constants;
import com.example.project1.entities.User;
import com.example.project1.entities.UserSession;
import com.example.project1.enums.SessionStatus;
import com.example.project1.exception.InvalidSessionException;
import com.example.project1.exception.UnauthorizedAccessException;
import com.example.project1.repository.UserSessionRepository;
import com.example.project1.services.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserSessionServiceImpl implements UserSessionService {
    private final UserSessionRepository userSessionRepository;
    @Override
    public boolean isValidSession(UUID session) {
        UserSession userSession = userSessionRepository.findById(session).orElseThrow(() -> new InvalidSessionException(Constants.ERROR_CODE.INVALID_SESSION));
        boolean isExpiredSession = userSession.getEndAt().isBefore(LocalDateTime.now(ZoneId.systemDefault()));
        if(userSession.getStatus() == SessionStatus.INACTIVE_SESSION.getValue() || isExpiredSession){
            return false;
        }
        return true;
    }

    @Override
    public UserSession createUserSession(User user) {
        UserSession userSession = new UserSession(user, LocalDateTime.now(ZoneId.systemDefault()).plusMinutes(60), 1);

        return userSessionRepository.save(userSession);
    }

    @Override
    public void disableSession(UUID session) {
        UserSession userSession = userSessionRepository.findById(session).orElseThrow(() -> new InvalidSessionException(Constants.ERROR_CODE.INVALID_SESSION));

        userSession.setStatus(SessionStatus.INACTIVE_SESSION.getValue());

        userSessionRepository.save(userSession);
    }

    @Override
    public void clearAllUserSession(User user) {
        userSessionRepository.deleteAllByUserId(user.getId());
    }
}
