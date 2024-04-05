package com.example.project1.controllers;

import com.example.project1.entities.User;
import com.example.project1.exception.AuthExistsEmailExeption;
import com.example.project1.exception.AuthExistsUsernameExeption;
import com.example.project1.exception.DatabaseSaveException;
import com.example.project1.payload.request.RegisterRequest;
import com.example.project1.payload.response.LoginResponse;
import com.example.project1.payload.response.ResponseMessage;
import com.example.project1.payload.request.LoginRequest;
import com.example.project1.repository.ResponseRepository;
import com.example.project1.repository.UserRepository;
import com.example.project1.security.UserDetailsServiceSecurity;
import com.example.project1.utitilies.JwtUtilily;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final UserDetailsServiceSecurity userDetailsServiceSecurity;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtilily jwtUtilily;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, UserDetailsServiceSecurity userDetailsServiceSecurity, PasswordEncoder passwordEncoder, JwtUtilily jwtUtilily) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userDetailsServiceSecurity = userDetailsServiceSecurity;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtilily = jwtUtilily;
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
    public ResponseEntity<ResponseRepository> register(
            @RequestBody RegisterRequest registerRequest){
        if(userRepository.existsByUsername(registerRequest.getUsername())){
            throw new AuthExistsUsernameExeption();
        }
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new AuthExistsEmailExeption();
        }
        LocalDateTime createdAt = LocalDateTime.now(ZoneId.systemDefault());
        LocalDateTime updatedAt = LocalDateTime.now(ZoneId.systemDefault());

        String passwordEncode = passwordEncoder.encode(registerRequest.getPassword());

        User user = new User(registerRequest.getUsername(), registerRequest.getEmail(), passwordEncode,0,null,1,1,0,createdAt,updatedAt);

        User userSaved =  userRepository.save(user);

        if(userSaved == null){
            throw new DatabaseSaveException();
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage<>(true, HttpStatus.OK.value(), "success"));
    }
}
