package com.example.project1.mapper;

import com.example.project1.entities.Book;
import com.example.project1.payload.request.BookCreateRequest;
import com.example.project1.payload.response.BookResponse;

import java.util.List;

public interface BookMapper {
    List<BookResponse> listBookToListBookResponse(List<Book> book);

    BookResponse bookToBookResponse(Book book);

    Book bookCreateRequestToBook(BookCreateRequest bookCreateRequest);

    Book bookCreateRequestToGivenBook(BookCreateRequest bookCreateRequest, Book book);
}
