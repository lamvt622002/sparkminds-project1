package com.example.project1.services.impl;

import com.example.project1.constants.Constants;
import com.example.project1.entities.Authorities;
import com.example.project1.entities.RefreshToken;
import com.example.project1.entities.User;
import com.example.project1.entities.UserSession;
import com.example.project1.enums.Role;
import com.example.project1.enums.SessionStatus;
import com.example.project1.enums.UserStatus;
import com.example.project1.exception.*;
import com.example.project1.payload.request.*;
import com.example.project1.payload.response.JWTPayLoad;
import com.example.project1.payload.response.LoginResponse;
import com.example.project1.repository.RefreshTokenRepository;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordGenerator passwordGenerator;
    private final UserSessionService userSessionService;
    private final UserSessionRepository userSessionRepository;
    private final GoogleAuthenticatorServiceImpl googleAuthenticatorService;

    public AuthServiceImpl(UserRepository userRepository, UserService userService, PasswordEncoder passwordEncoder, AuthoritiesService authoritiesService, JwtUtilily jwtUtility, SendingEmailService emailService, @Lazy AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UserDetailsServiceSecurity userDetailsServiceSecurity, RefreshTokenService refreshTokenService, RefreshTokenRepository refreshTokenRepository, PasswordGenerator passwordGenerator, UserSessionService userSessionService, UserSessionRepository userSessionRepository, GoogleAuthenticatorServiceImpl googleAuthenticatorService) {
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
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordGenerator = passwordGenerator;
        this.userSessionService = userSessionService;
        this.userSessionRepository = userSessionRepository;
        this.googleAuthenticatorService = googleAuthenticatorService;
    }

    @Override
    @Transactional
    public void register(RegisterRequest registerRequest) {
        validateRegisterRequest(registerRequest);

        String passwordEncode = passwordEncoder.encode(registerRequest.getPassword());

        Authorities defaultAuthority = authoritiesService.findAuthorityByAuthority(Role.USER.name())
                .orElseThrow(() -> new DataNotFoundException("Invalid authority"));

        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .birthDay(registerRequest.getBirthDay())
                .phoneNumber(registerRequest.getPhoneNumber())
                .email(registerRequest.getEmail())
                .password(passwordEncode)
                .failedLoginAttempts(0)
                .role(defaultAuthority)
                .status(UserStatus.INACTIVE.getValue())
                .build();

        User userSaved = userRepository.save(user);

        sendingEmailService.sendVerificationEmail(userSaved);
    }

    @Override
    public String login(LoginRequest loginRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

        User user = userRepository.findUserByEmail(userDetails.getUsername()).orElseThrow(() -> new DataNotFoundException(Constants.ERROR_CODE.USER_NOT_FOUND, userDetails.getUsername()));

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
            throw new BadRequestException("Invalid code");
        }

        User user = userRepository.findUserByEmail(googleValidateCodeRequest.getUserName()).orElseThrow(() -> new DataNotFoundException(Constants.ERROR_CODE.USER_NOT_FOUND, googleValidateCodeRequest.getUserName()));

        return LoginResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .birthDay(user.getBirthDay())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole().getAuthority())
                .status(UserStatus.fromValue(user.getStatus()))
                .build();
    }

    @Override
    public HttpHeaders responseCookies(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new DataNotFoundException(Constants.ERROR_CODE.USER_NOT_FOUND, email));

        final UserSession userSession = userSessionService.createUserSession(user);

        JWTPayLoad  jwtPayLoad = new JWTPayLoad(user.getId(), user.getEmail(), userSession.getSessionId().toString());

        final String accessToken = jwtUtility.generateToken(jwtPayLoad, 30);

        final RefreshToken refreshToken = refreshTokenService.createRefreshToken(jwtPayLoad);

        ResponseCookie access_Token = ResponseCookie.from("access_token", accessToken)
                .httpOnly(false)
                .secure(true)
                .path("/")
                .maxAge(2* 24 * 60 * 60)
                .domain("localhost")
                .build();
        ResponseCookie refresh_Token = ResponseCookie.from("refresh_token", refreshToken.getToken())
                .httpOnly(false)
                .secure(true)
                .path("/")
                .maxAge(2* 24 * 60 * 60)
                .domain("localhost")
                .build();
        ResponseCookie session = ResponseCookie.from("session", userSession.getSessionId().toString())
                .httpOnly(false)
                .secure(true)
                .path("/")
                .maxAge(2* 24 * 60 * 60)
                .domain("localhost")
                .build();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, access_Token.toString());
        responseHeaders.add(HttpHeaders.SET_COOKIE, refresh_Token.toString());
        responseHeaders.add(HttpHeaders.SET_COOKIE, session.toString());
        return responseHeaders;
    }

    @Override
    public String refreshToken(RefreshTokenRequest request) {
        String token = request.getRefreshToken();
        String email = null;
        Map<String, Object> claims;

        try{
            email = jwtUtility.extractUserName(token);
        }catch (ExpiredJwtException ex){
            refreshTokenRepository.delete(refreshTokenService.findByToken(token).orElseThrow(() -> new DataNotFoundException("Refresh token not found")));
            claims = ex.getClaims();
            UUID sessionId = UUID.fromString(claims.get("session").toString());
            UserSession userSession = userSessionRepository.findById(sessionId).orElseThrow(() -> new BadRequestException("Session not found"));
            userSession.setStatus(SessionStatus.INACTIVE_SESSION.getValue());
            userSessionRepository.save(userSession);
            throw new InvalidSessionException(Constants.ERROR_CODE.EXPIRED_SESSION);
        }

        claims = jwtUtility.extractAllClaim(token);

        UUID sessionId = UUID.fromString(claims.get("session").toString());

        if(!userSessionService.isValidSession(sessionId)){
            throw new InvalidSessionException(Constants.ERROR_CODE.INVALID_SESSION);
        }

        String finalEmail = email;
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new DataNotFoundException(Constants.ERROR_CODE.USER_NOT_FOUND, finalEmail));

        final UserSession userSession = userSessionService.createUserSession(user);

        JWTPayLoad  jwtPayLoad = new JWTPayLoad(user.getId(), user.getEmail(), userSession.getSessionId().toString());

        String newAccessToken = refreshTokenService.findByToken(token)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(u -> {
                    return jwtUtility.generateToken(jwtPayLoad, 1);
                }).orElseThrow(() -> new DataNotFoundException(Constants.ERROR_CODE.REFRESH_TOKEN_NOT_FOUND));

        return newAccessToken;
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

    private void validateRegisterRequest(RegisterRequest registerRequest) {
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new BadRequestException(Constants.ERROR_CODE.INVALID_PASSWORD_AND_CONFIRM_PASSWORD);
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new DataIntegrityViolationException(Constants.ERROR_CODE.DUPLICATE_OLD_EMAIL_AND_NEW_EMAIL);
        }
        if (userRepository.existsByPhoneNumber(registerRequest.getPhoneNumber())) {
            throw new DataIntegrityViolationException(Constants.ERROR_CODE.PHONE_NUMBER_ALREADY_EXISTS);
        }
    }
}
