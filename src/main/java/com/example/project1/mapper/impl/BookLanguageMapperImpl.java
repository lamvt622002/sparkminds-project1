package com.example.project1.mapper.impl;

import com.example.project1.entities.BookLanguage;
import com.example.project1.entities.Category;
import com.example.project1.mapper.BookLanguageMapper;
import com.example.project1.payload.response.BookLanguageResponse;
import com.example.project1.payload.response.BookResponse;
import com.example.project1.payload.response.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookLanguageMapperImpl implements BookLanguageMapper {
    private final ModelMapper mapper;
    @Override
    public List<BookLanguageResponse> listBookLanguageToListBookLanguageResponse(List<BookLanguage> bookLanguages) {
        Converter<BookLanguage, BookLanguageResponse> bookLanguageResponseConverter = context -> {
            BookLanguage src = context.getSource();
            return BookLanguageResponse.builder()
                    .id(src.getId())
                    .languageName(src.getLanguageName())
                    .build();
        };
        TypeMap<BookLanguage, BookLanguageResponse> typeMap = mapper.getTypeMap(BookLanguage.class, BookLanguageResponse.class);
        if (typeMap == null) {
            mapper.createTypeMap(BookLanguage.class, BookLanguageResponse.class).setConverter(bookLanguageResponseConverter);
        }
        return bookLanguages.stream().map(book -> mapper.map(book, BookLanguageResponse.class)).collect(Collectors.toList());
    }
}
