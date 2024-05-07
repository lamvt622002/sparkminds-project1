package com.example.project1.controllers;

import com.example.project1.mapper.BookMapper;
import com.example.project1.payload.request.BookCreateRequest;
import com.example.project1.payload.response.BookResponse;
import com.example.project1.payload.response.ResponseMessage;
import com.example.project1.payload.response.ResponseRepository;
import com.example.project1.repository.BookRepository;
import com.example.project1.repository.specs.CustomBookRepository;
import com.example.project1.services.BookService;
import com.example.project1.services.FileImportService;
import com.example.project1.services.ImageUploadService;
import com.example.project1.services.criteria.BookCriteria;
import com.example.project1.services.criteria.TimeCriteria;
import com.example.project1.services.impl.BookQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class TestController {
    private final CustomBookRepository customBookRepository;

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    private final ImageUploadService imageUploadService;

    private final BookService bookService;

    private final FileImportService fileImportService;

    private final BookQueryService bookQueryService;

    @PostMapping("/image")
    public String upload(@RequestParam("file") MultipartFile multipartFile) {
        return imageUploadService.upload(multipartFile);
    }
    @PostMapping(value = "/create-book", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void upload(@Valid @ModelAttribute BookCreateRequest bookCreateRequest) {
        bookService.addBook(bookCreateRequest);
    }

    @PutMapping(value = "/edit-book/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> edit(@Valid @ModelAttribute BookCreateRequest bookCreateRequest, @PathVariable Long id) {
        bookService.editBook(bookCreateRequest, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/create-csv-book", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void csvBook(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        fileImportService.readBooksFromCsv(multipartFile.getInputStream());
    }

    @GetMapping("/filter-book")
    public ResponseEntity<ResponseRepository> filter(BookCriteria request, TimeCriteria  timeCriteria, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage<>(true, HttpStatus.OK.value(),  bookQueryService.findByCriteria(request, timeCriteria, pageable)));
    }

    @PostMapping("/test-image")
    public ResponseEntity<ResponseRepository> testImage(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage<>(true, HttpStatus.OK.value(), imageUploadService.uploadFileToServer(multipartFile)));
    }
}
