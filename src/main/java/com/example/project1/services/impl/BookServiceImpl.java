package com.example.project1.services.impl;

import com.example.project1.entities.Book;
import com.example.project1.entities.BookLanguage;
import com.example.project1.entities.Category;
import com.example.project1.entities.Publisher;
import com.example.project1.exception.BadRequestException;
import com.example.project1.mapper.BookMapper;
import com.example.project1.payload.request.BookCreateRequest;
import com.example.project1.payload.response.BookResponse;
import com.example.project1.repository.BookLanguageRepository;
import com.example.project1.repository.BookRepository;
import com.example.project1.repository.CategoryRepository;
import com.example.project1.repository.PublisherRepository;
import com.example.project1.services.BookService;
import com.example.project1.services.FileImportService;
import com.example.project1.services.ImageUploadService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    private final ImageUploadService imageUploadService;

    private final FileImportService fileImportService;

    @Override
    @Transactional
    public void addBook(BookCreateRequest bookRequest) {
        imageUploadService.validateImageFile(bookRequest.getImage());
        Book book = bookMapper.bookCreateRequestToBook(bookRequest);
        String imageLink = imageUploadService.upload(bookRequest.getImage());
        book.setImage(imageLink);
        Book test = bookRepository.save(book);
    }

    @Override
    public void addBooksFromCsvFile(MultipartFile multipartFile) throws IOException {
        fileImportService.validateCsvFormat(multipartFile);
        List<Book> books = fileImportService.readBooksFromCsv(multipartFile.getInputStream());
        bookRepository.saveAll(books);
    }

    @Override
    @Transactional
    public void editBook(BookCreateRequest bookCreateRequest, Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BadRequestException("Book not found"));
        Book updatedBook = bookMapper.bookCreateRequestToGivenBook(bookCreateRequest,book);
        if(!(bookCreateRequest.getImage() ==null)){
            String imageLink = imageUploadService.upload(bookCreateRequest.getImage());
            updatedBook.setImage(imageLink);
        }
        bookRepository.save(updatedBook);
    }

    @Override
    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BadRequestException("Book not found"));
        book.setIsDelete(1);
        bookRepository.save(book);
    }

    @Override
    public BookResponse getBookById(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BadRequestException("Book not found"));
        return bookMapper.bookToBookResponse(book);
    }
}
