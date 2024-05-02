package com.example.project1.services;

import org.springframework.http.HttpHeaders;

public interface CookiesService {
    HttpHeaders responseCookies(String email);
}
