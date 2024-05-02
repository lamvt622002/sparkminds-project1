package com.example.project1.mapper;

import com.example.project1.entities.Book;
import com.example.project1.payload.dto.BookDto;

import java.util.List;

public interface BookMapper {
    List<BookDto> listBookToListBookDto(List<Book> book);
}
