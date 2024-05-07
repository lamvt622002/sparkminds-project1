package com.example.project1.mapper.impl;

import com.example.project1.entities.*;
import com.example.project1.exception.BadRequestException;
import com.example.project1.mapper.BookMapper;
import com.example.project1.payload.request.BookCreateRequest;
import com.example.project1.payload.response.BookResponse;
import com.example.project1.payload.response.LoginResponse;
import com.example.project1.repository.AuthorRepository;
import com.example.project1.repository.BookLanguageRepository;
import com.example.project1.repository.CategoryRepository;
import com.example.project1.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookMapperImpl implements BookMapper {
    private final ModelMapper mapper;

    private final BookLanguageRepository bookLanguageRepository;

    private final PublisherRepository publisherRepository;

    private final CategoryRepository categoryRepository;

    private final AuthorRepository authorRepository;

    @Override
    public List<BookResponse> listBookToListBookResponse(List<Book> books) {
        Converter<Book, BookResponse> bookResponseConverter = context -> {
            Book src = context.getSource();
            return BookResponse.builder()
                    .id(src.getId())
                    .title(src.getTitle())
                    .language(src.getLanguage().getLanguageName())
                    .category(src.getCategory().getCategoryName())
                    .publisher(src.getPublisher().getPublisherName())
                    .price(src.getPrice())
                    .numOfPages(src.getNumOfPages())
                    .description(src.getDescription())
                    .quantity(src.getQuantity())
                    .image(src.getImage())
                    .authorName(src.getAuthors().stream().map(author -> {
                        String fullName = author.getFirstName() + " " + author.getLastName();
                        return fullName;
                    }).toList())
                    .createdAt(src.getCreatedAt())
                    .updatedAt(src.getUpdatedAt())
                    .build();
        };
        mapper.createTypeMap(Book.class, BookResponse.class).setConverter(bookResponseConverter);

        return books.stream().map(book -> mapper.map(book, BookResponse.class)).collect(Collectors.toList());
    }

    @Override
    public BookResponse bookToBookResponse(Book book) {
        Converter<Book, BookResponse> bookResponseConverter = context -> {
            Book src = context.getSource();
            return BookResponse.builder()
                    .id(src.getId())
                    .title(src.getTitle())
                    .language(src.getLanguage().getLanguageName())
                    .category(src.getCategory().getCategoryName())
                    .publisher(src.getPublisher().getPublisherName())
                    .price(src.getPrice())
                    .numOfPages(src.getNumOfPages())
                    .description(src.getDescription())
                    .quantity(src.getQuantity())
                    .image(src.getImage())
                    .authorName(src.getAuthors().stream().map(author -> {
                        String fullName = author.getFirstName() + " " + author.getLastName();
                        return fullName;
                    }).toList())
                    .createdAt(src.getCreatedAt())
                    .updatedAt(src.getUpdatedAt())
                    .build();
        };

        TypeMap<Book, BookResponse> typeMap = mapper.getTypeMap(Book.class, BookResponse.class);
        if (typeMap == null) {
            mapper.createTypeMap(Book.class, BookResponse.class).setConverter(bookResponseConverter);
        }
        return mapper.map(book, BookResponse.class);
    }

    @Override
    public Book bookCreateRequestToBook(BookCreateRequest bookCreateRequest) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        BookLanguage bookLanguage = bookLanguageRepository.findById(bookCreateRequest.getLanguageId()).orElseThrow(() -> new BadRequestException("Invalid book language"));
        Category category = categoryRepository.findById(bookCreateRequest.getCategoryId()).orElseThrow(() -> new BadRequestException("Invalid category"));
        Publisher publisher = publisherRepository.findById(bookCreateRequest.getPublisherId()).orElseThrow(() -> new BadRequestException("Invalid publisher"));
        Author author=authorRepository.findById(bookCreateRequest.getAuthorId()).orElseThrow(() -> new BadRequestException("Invalid author"));
        Converter<BookCreateRequest, Book> bookConverter = context -> {
            BookCreateRequest src = context.getSource();
            Book book = new Book();
            book.setTitle(src.getTitle());
            book.setLanguage(bookLanguage);
            book.setCategory(category);
            book.setPublisher(publisher);
            book.getAuthors().add(author);
            book.setPrice(src.getPrice());
            book.setNumOfPages(src.getNumOfPages());
            book.setDescription(src.getDescription());
            book.setQuantity(src.getQuantity());
            book.setIsDelete(0);
            return book;
        };

        TypeMap<BookCreateRequest, Book> typeMap = mapper.getTypeMap(BookCreateRequest.class, Book.class);
        if (typeMap == null) {
            mapper.createTypeMap(BookCreateRequest.class, Book.class).setConverter(bookConverter);
        }

        return mapper.map(bookCreateRequest, Book.class);
    }

    @Override
    @Transactional
    public Book bookCreateRequestToGivenBook(BookCreateRequest bookCreateRequest, Book book) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        BookLanguage bookLanguage = bookLanguageRepository.findById(bookCreateRequest.getLanguageId()).orElseThrow(() -> new BadRequestException("Invalid book language"));
        Category category = categoryRepository.findById(bookCreateRequest.getCategoryId()).orElseThrow(() -> new BadRequestException("Invalid category"));
        Publisher publisher = publisherRepository.findById(bookCreateRequest.getPublisherId()).orElseThrow(() -> new BadRequestException("Invalid publisher"));
        Author author=authorRepository.findById(bookCreateRequest.getAuthorId()).orElseThrow(() -> new BadRequestException("Invalid author"));
        Converter<BookCreateRequest, Book> bookConverter = context -> {
            BookCreateRequest src = context.getSource();
            book.setTitle(src.getTitle());
            book.setLanguage(bookLanguage);
            book.setCategory(category);
            book.setPublisher(publisher);
            book.getAuthors().add(author);
            book.setPrice(src.getPrice());
            book.setNumOfPages(src.getNumOfPages());
            book.setDescription(src.getDescription());
            book.setQuantity(src.getQuantity());
            book.setIsDelete(0);
            return book;
        };

        TypeMap<BookCreateRequest, Book> typeMap = mapper.getTypeMap(BookCreateRequest.class, Book.class);
        if (typeMap == null) {
            mapper.createTypeMap(BookCreateRequest.class, Book.class).setConverter(bookConverter);
        }

        return mapper.map(bookCreateRequest, Book.class);
    }
}
