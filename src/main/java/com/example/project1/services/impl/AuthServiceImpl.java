package com.example.project1.services.impl;

import com.example.project1.constants.Constants;
import com.example.project1.entities.User;
import com.example.project1.entities.UserSession;
import com.example.project1.enums.UserStatus;
import com.example.project1.exception.*;
import com.example.project1.mapper.CustomerMapper;
import com.example.project1.payload.request.*;
import com.example.project1.payload.response.JWTPayLoad;
import com.example.project1.payload.response.LoginResponse;
import com.example.project1.repository.CustomerRepository;
import com.example.project1.repository.UserRepository;
import com.example.project1.repository.UserSessionRepository;
import com.example.project1.security.UserDetailsSecurity;
import com.example.project1.security.UserDetailsServiceSecurity;
import com.example.project1.services.*;
import com.example.project1.utitilies.JwtUtilily;
import com.example.project1.utitilies.PasswordGenerator;
import com.google.zxing.WriterException;
import io.jsonwebtoken.ExpiredJwtException;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthoritiesService authoritiesService;
    private final JwtUtilily jwtUtility;
    private final SendingEmailService sendingEmailService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserDetailsServiceSecurity userDetailsServiceSecurity;
    private final RefreshTokenService refreshTokenService;
    private final PasswordGenerator passwordGenerator;
    private final UserSessionService userSessionService;
    private final UserSessionRepository userSessionRepository;
    private final GoogleAuthenticatorServiceImpl googleAuthenticatorService;
    private final CustomerMapper userMapper;
    private final CustomerRepository customerRepository;

    public AuthServiceImpl(UserRepository userRepository, UserService userService, PasswordEncoder passwordEncoder, AuthoritiesService authoritiesService, JwtUtilily jwtUtility, SendingEmailService emailService, @Lazy AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UserDetailsServiceSecurity userDetailsServiceSecurity, RefreshTokenService refreshTokenService, PasswordGenerator passwordGenerator, UserSessionService userSessionService, UserSessionRepository userSessionRepository, GoogleAuthenticatorServiceImpl googleAuthenticatorService, CustomerMapper userMapper, CustomerRepository customerRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authoritiesService = authoritiesService;
        this.jwtUtility = jwtUtility;
        this.sendingEmailService = emailService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userDetailsServiceSecurity = userDetailsServiceSecurity;
        this.refreshTokenService = refreshTokenService;
        this.passwordGenerator = passwordGenerator;
        this.userSessionService = userSessionService;
        this.userSessionRepository = userSessionRepository;
        this.googleAuthenticatorService = googleAuthenticatorService;
        this.userMapper = userMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public String login(LoginRequest loginRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        String qrCode;

        try {
            qrCode = googleAuthenticatorService.generateQR(loginRequest.getEmail(), response);
        } catch (WriterException | IOException e) {
            throw new RuntimeException(e);
        }
        return qrCode;
    }

    @Override
    public LoginResponse twoFactorAuthenticate(GoogleValidateCodeRequest googleValidateCodeRequest) {
        if(!googleAuthenticatorService.isInvalidCode(googleValidateCodeRequest)){
            throw new BadRequestException(Constants.ERROR_CODE.INVALID_CODE, googleValidateCodeRequest.getCode());
        }

        User user = userRepository.findUserByEmail(googleValidateCodeRequest.getUserName()).orElseThrow(() -> new DataNotFoundException(Constants.ERROR_CODE.USER_NOT_FOUND, googleValidateCodeRequest.getUserName()));

        return userMapper.userToLoginResponse(user);
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetailsSecurity userDetail = userDetailsServiceSecurity.loadUserByUsername(username);

        User user = userDetail.getUser();

        boolean isPasswordMarches = passwordEncoder.matches(password, userDetail.getPassword());

        if (user.getStatus() == UserStatus.INACTIVE.getValue()) {
            throw new UnauthorizedAccessException(Constants.ERROR_CODE.VERIFY_EMAIL);
        }else if(user.getStatus() == UserStatus.RESET_PASSWORD.getValue()){
            throw new UnauthorizedAccessException(Constants.ERROR_CODE.REQUIRE_CHANGE_PASSWORD);
        }else if(user.getStatus() == UserStatus.LOCK.getValue() && user.getLockoutTime().isAfter(LocalDateTime.now(ZoneId.systemDefault()))){
            throw new UnauthorizedAccessException(Constants.ERROR_CODE.BLOCK_ACCOUNT);
        }

        if (user.getStatus() == -1 && user.getFailedLoginAttempts() == 3) {
            if (user.getLockoutTime().isAfter(LocalDateTime.now(ZoneId.systemDefault()))) {
                Duration duration = Duration.between(LocalDateTime.now(ZoneId.systemDefault()), user.getLockoutTime());

                long remainingMinutes = duration.toMinutes();

                long remainingSeconds = duration.minusMinutes(remainingMinutes).getSeconds();

                throw new BadCredentialsException("Please try again. Remaining lockout time: " + remainingMinutes + " minutes and " + remainingSeconds + " seconds");
            } else {
                if (!isPasswordMarches) {
                    user.setLockoutTime(LocalDateTime.now(ZoneId.systemDefault()).plusMinutes(30));
                    userService.saveUser(user);
                } else {
                    user.setStatus(1);
                    user.setFailedLoginAttempts(0);
                    user.setLockoutTime(null);
                    userService.saveUser(user);
                }
            }
        }

        if (isPasswordMarches) {
            if (user.getFailedLoginAttempts() != 0) {
                user.setFailedLoginAttempts(0);
                userService.saveUser(user);
            }
            return new UsernamePasswordAuthenticationToken(username, password, userDetail.getAuthorities());
        } else {
            if (user.getFailedLoginAttempts() < 2) {
                user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
                userService.saveUser(user);
            } else if (user.getFailedLoginAttempts() == 2) {
                user.setStatus(-1);
                user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
                user.setLockoutTime(LocalDateTime.now(ZoneId.systemDefault()).plusMinutes(30));
                userService.saveUser(user);
            }
            throw new BadRequestException(Constants.ERROR_CODE.INVALID_PASSWORD);
        }
    }

    @Override
    public void resentVerifyLink(EmailRequest request) {
        User user = userRepository.findUserByEmail(request.getEmail()).orElseThrow(() -> new DataNotFoundException(Constants.ERROR_CODE.EMAIL_NOT_FOUND));

        if(user.getStatus() != UserStatus.INACTIVE.getValue()){
            throw new BadRequestException("Your account was verified");
        }
        sendingEmailService.sendVerificationEmail(user);
    }

    @Override
    public void resentOtpVerification(EmailRequest request) {
        User user = userRepository.findUserByEmail(request.getEmail()).orElseThrow(() -> new DataNotFoundException(Constants.ERROR_CODE.USER_NOT_FOUND, request.getEmail()));

        if(user.getStatus() != UserStatus.INACTIVE.getValue()){
            throw new UnauthorizedAccessException(Constants.ERROR_CODE.ACCOUNT_WAS_VERIFY);
        }
        sendingEmailService.sentOtpVerification(user);
    }

    @Override
    public void resetPassword(EmailRequest request) {
        User user = userRepository.findUserByEmail(request.getEmail()).orElseThrow(() -> new DataNotFoundException(Constants.ERROR_CODE.USER_NOT_FOUND, request.getEmail()));

        if(user.getStatus() == UserStatus.INACTIVE.getValue() || user.getStatus() == UserStatus.LOCK.getValue()){
            throw new BadRequestException(Constants.ERROR_CODE.DONT_HAVE_PERMISSION);
        }
        String newPassword = passwordGenerator.generatePassword();

        String encodePassword = passwordEncoder.encode(newPassword);

        user.setPassword(encodePassword);

        user.setStatus(UserStatus.RESET_PASSWORD.getValue());

        userRepository.save(user);

        sendingEmailService.sendResetPassword(user, newPassword);
    }

    @Override
    public void changePassword(ChangePasswordWithoutAuthRequest request) {
        User user = userRepository.findUserByEmail(request.getEmail()).orElseThrow(() -> new DataNotFoundException(Constants.ERROR_CODE.USER_NOT_FOUND, request.getEmail()));

        if(user.getStatus() != UserStatus.RESET_PASSWORD.getValue()){
            throw new UnauthorizedAccessException(Constants.ERROR_CODE.DONT_HAVE_PERMISSION);
        }

        if(!passwordEncoder.matches(request.getOldPassword(), user.getPassword())){
            throw new BadRequestException(Constants.ERROR_CODE.INVALID_PASSWORD);
        }

        String encodePassword = passwordEncoder.encode(request.getNewPassword());

        user.setPassword(encodePassword);

        user.setStatus(UserStatus.ACTIVE.getValue());

        userRepository.save(user);
    }
}
