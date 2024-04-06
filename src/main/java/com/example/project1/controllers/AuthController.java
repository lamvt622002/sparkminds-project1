package com.example.project1.controllers;

import com.example.project1.entities.EmailVerification;
import com.example.project1.entities.User;
import com.example.project1.exception.*;
import com.example.project1.payload.request.RegisterRequest;
import com.example.project1.payload.response.LoginResponse;
import com.example.project1.payload.response.RegisterResponse;
import com.example.project1.payload.response.ResponseMessage;
import com.example.project1.payload.request.LoginRequest;
import com.example.project1.repository.ResponseRepository;
import com.example.project1.repository.UserRepository;
import com.example.project1.security.UserDetailsServiceSecurity;
import com.example.project1.services.impl.EmailServiceImpl;
import com.example.project1.services.impl.EmailVerificationServiceImpl;
import com.example.project1.services.impl.UserServiceImpl;
import com.example.project1.utitilies.JwtUtilily;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    private final UserDetailsServiceSecurity userDetailsServiceSecurity;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtilily jwtUtilily;
    private final EmailServiceImpl emailService;
    private final SimpleMailMessage simpleMailMessage;
    private final EmailVerificationServiceImpl emailVerificationService;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, UserServiceImpl userService, UserDetailsServiceSecurity userDetailsServiceSecurity, PasswordEncoder passwordEncoder, JwtUtilily jwtUtilily, EmailServiceImpl emailService, SimpleMailMessage simpleMailMessage, EmailVerificationServiceImpl emailVerificationService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userService = userService;
        this.userDetailsServiceSecurity = userDetailsServiceSecurity;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtilily = jwtUtilily;
        this.emailService = emailService;
        this.simpleMailMessage = simpleMailMessage;
        this.emailVerificationService = emailVerificationService;
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseRepository> login(
            @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));


        UserDetails userDetails = userDetailsServiceSecurity.loadUserByUsername(loginRequest.getEmail());

        final String token = jwtUtilily.generateToken(userDetails);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage<>(true, HttpStatus.OK.value(),new LoginResponse(loginRequest.getEmail(), token)));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseRepository> register( @RequestBody RegisterRequest registerRequest){

        if(userService.existsByUsername(registerRequest.getUsername())){
                throw new DataIntegrityViolationException("Username already exists in the database");
        }
        if(userService.existsByEmail(registerRequest.getEmail())){
            throw new DataIntegrityViolationException("Email already exists in the database");
        }
        LocalDateTime createdAt = LocalDateTime.now(ZoneId.systemDefault());
        LocalDateTime updatedAt = LocalDateTime.now(ZoneId.systemDefault());

        String passwordEncode = passwordEncoder.encode(registerRequest.getPassword());

        User user = new User(registerRequest.getUsername(), registerRequest.getEmail(), passwordEncode,0,null,1,0,0,createdAt,updatedAt);

        User userSaved =  userService.saveUser(user);

        String token = jwtUtilily.generateTokenForEmail(registerRequest.getEmail());

        System.out.println(token);

        EmailVerification emailVerification = new EmailVerification(userSaved.getId(),userSaved.getEmail(), token, createdAt, createdAt.plusHours(2));

        emailVerificationService.saveEmailVerificationToken(emailVerification);

        String text = String.format(simpleMailMessage.getText(), "http://localhost:8080/api/auth/verify-email/" + token);

        emailService.sendMessage(registerRequest.getEmail(), "Please verify your email for your account", text);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage<>(true, HttpStatus.OK.value(), new RegisterResponse(userSaved.getId(),userSaved.getUsername(),userSaved.getEmail())));
    }

    @GetMapping("/verify-email/{token}")
    public ResponseEntity<ResponseRepository> verifyEmail( @PathVariable String token){
        if(token.isBlank()){
            throw new BadRequestException("Data not found");
        }
        boolean isTokenExpire = jwtUtilily.isTokenExpired(token);
        System.out.println(isTokenExpire);

        String email = jwtUtilily.extractUserName(token);

        User user = userService.findUserByEmail(email);

        if(isTokenExpire){
            throw new BadRequestException("Token is expired");
        }
        emailVerificationService.verifyEmail(token, user);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage<>(true, HttpStatus.OK.value(), new RegisterResponse(user.getId(),user.getUsername(), user.getEmail())));
    }
}
