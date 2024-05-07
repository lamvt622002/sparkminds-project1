package com.example.project1.mapper;

import com.example.project1.entities.Author;
import com.example.project1.payload.response.AuthorResponse;

import java.util.List;

public interface AuthorMapper {
    List<AuthorResponse> listAuthorToListAuthorResponse(List<Author> authors);
}
