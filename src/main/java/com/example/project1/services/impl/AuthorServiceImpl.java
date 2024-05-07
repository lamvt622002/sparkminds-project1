package com.example.project1.services.impl;

import com.example.project1.mapper.AuthorMapper;
import com.example.project1.payload.response.AuthorResponse;
import com.example.project1.repository.AuthorRepository;
import com.example.project1.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorMapper authorMapper;

    private final AuthorRepository authorRepository;
    @Override
    public List<AuthorResponse> getAllAuthor() {
        return authorMapper.listAuthorToListAuthorResponse(authorRepository.findAll());
    }
}
