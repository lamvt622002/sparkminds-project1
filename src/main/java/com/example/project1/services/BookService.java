package com.example.project1.services;

import com.example.project1.payload.request.BookCreateRequest;
import com.example.project1.payload.response.BookResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BookService {
    void addBook(BookCreateRequest bookRequest);

    void addBooksFromCsvFile(MultipartFile multipartFile) throws IOException;

    void editBook(BookCreateRequest bookCreateRequest, Long bookId);

    void deleteBook(Long bookId);

    BookResponse getBookById(Long bookId);
}
