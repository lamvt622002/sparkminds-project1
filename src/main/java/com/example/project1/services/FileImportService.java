package com.example.project1.services;

import com.example.project1.entities.Book;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface FileImportService {
    List<Book> readBooksFromCsv(InputStream inputStream);

    void validateCsvFormat(MultipartFile multipartFile);
}
