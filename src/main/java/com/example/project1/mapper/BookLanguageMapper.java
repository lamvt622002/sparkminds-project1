package com.example.project1.mapper;

import com.example.project1.entities.BookLanguage;
import com.example.project1.entities.Category;
import com.example.project1.payload.response.BookLanguageResponse;

import java.util.List;

public interface BookLanguageMapper {
    List<BookLanguageResponse> listBookLanguageToListBookLanguageResponse(List<BookLanguage> bookLanguages);
}
