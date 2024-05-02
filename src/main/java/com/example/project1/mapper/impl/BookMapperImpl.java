package com.example.project1.mapper.impl;

import com.example.project1.entities.Author;
import com.example.project1.entities.Book;
import com.example.project1.entities.Test;
import com.example.project1.mapper.BookMapper;
import com.example.project1.payload.dto.BookDto;
import com.example.project1.payload.dto.TestDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookMapperImpl implements BookMapper {
    private final ModelMapper mapper;

    @Override
    public List<BookDto> listBookToListBookDto(List<Book> books) {
        Converter<Book, BookDto> bookDtoConverter = context -> {
            Book src = context.getSource();
            return BookDto.builder()
                    .id(src.getId())
                    .title(src.getTitle())
                    .language(src.getLanguage().getLanguageName())
                    .category(src.getCategory().getCategory_name())
                    .publisher(src.getPublisher().getPublisher_name())
                    .price(src.getPrice())
                    .numOfPages(src.getNumOfPages())
                    .description(src.getDescription())
                    .quantity(src.getQuantity())
                    .image(src.getImage())
                    .authorsFirstName(src.getAuthors().stream().map(Author::getFirstName).toList())
                    .build();
        };
        mapper.createTypeMap(Book.class, BookDto.class).setConverter(bookDtoConverter);
//        mapper.typeMap(Book.class, BookDto.class)
//                .addMapping(src -> {
//                    if(src == null ||src.getAuthors() == null){
//                        return null;
//                    }
//                    return src.getAuthors().stream().map(Author::getFirstName).collect(Collectors.toList());
//
//                }, BookDto::setAuthorsFirstName);

        return books.stream().map(book -> mapper.map(book, BookDto.class)).collect(Collectors.toList());
    }
}
