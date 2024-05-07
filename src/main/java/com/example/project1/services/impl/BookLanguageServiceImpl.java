package com.example.project1.services.impl;

import com.example.project1.mapper.BookLanguageMapper;
import com.example.project1.payload.response.BookLanguageResponse;
import com.example.project1.repository.BookLanguageRepository;
import com.example.project1.services.BookLanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookLanguageServiceImpl implements BookLanguageService {
    private final BookLanguageMapper bookLanguageMapper;

    private final BookLanguageRepository bookLanguageRepository;
    @Override
    public List<BookLanguageResponse> getAllBookLanguage() {
        return bookLanguageMapper.listBookLanguageToListBookLanguageResponse(bookLanguageRepository.findAll());
    }
}
