package com.example.project1.services;

import com.example.project1.payload.response.AuthorResponse;

import java.util.List;

public interface AuthorService {
    List<AuthorResponse> getAllAuthor();
}
