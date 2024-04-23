package com.example.project1.controllers;

import com.example.project1.payload.response.ResponseMessage;
import com.example.project1.repository.AuthoritiesRepository;
import com.example.project1.payload.response.ResponseRepository;
import com.example.project1.services.impl.TestService;
import com.example.project1.utitilies.JwtUtilily;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TestController {
    private final TestService testService;
    private final JwtUtilily jwtUtilily;

    private final AuthoritiesRepository authoritiesRepository;

    public TestController(TestService testService, JwtUtilily jwtUtilily, AuthoritiesRepository authoritiesRepository) {
        this.testService = testService;
        this.jwtUtilily = jwtUtilily;
        this.authoritiesRepository = authoritiesRepository;
    }

    @GetMapping("/test")
    public ResponseEntity<ResponseRepository> test(){
            return null;
    }

    @GetMapping("/setCookie")
    public ResponseEntity<?> setCookie(){
        ResponseCookie resCookie = ResponseCookie.from("user-id", "c2FtLnNtaXRoQGV4YW1wbGUuY29t")
                .httpOnly(false)
                .secure(true)
                .path("/")
                .maxAge(1 * 24 * 60 * 60)
                .domain("localhost")
                .build();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, resCookie.toString());

        return ResponseEntity.ok().headers(responseHeaders).build();
    }

    @GetMapping("/test-lombok")
    public ResponseEntity<ResponseRepository> testLombok(){
       

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage<>(true, HttpStatus.OK.value(), "heheheh"));
    }
}
