package com.example.project1.controllers;

import com.example.project1.payload.request.BookCreateRequest;
import com.example.project1.payload.response.ResponseMessage;
import com.example.project1.payload.response.ResponseRepository;
import com.example.project1.repository.specs.CustomBookRepository;
import com.example.project1.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {
    private final CustomBookRepository customBookRepository;

    private final BookService bookService;

    @GetMapping("/all-book")
    public ResponseEntity<ResponseRepository> allBook(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage<>(true, HttpStatus.OK.value(),customBookRepository.findAllBook(PageRequest.of(page, size))));
    }

    @GetMapping("/get-book/{id}")
    public ResponseEntity<ResponseRepository> getABook(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage<>(true, HttpStatus.OK.value(),bookService.getBookById(id)));
    }

    @GetMapping("/filter")
    public ResponseEntity<ResponseRepository> filterBook(
            @RequestParam(value = "category", defaultValue = "") Long categoryId,
            @RequestParam(value = "language", defaultValue = "") Long languageId,
            @RequestParam(value = "publisher", defaultValue = "") Long publisherId,
            @RequestParam(value = "min_price", defaultValue = "") Double minPrice,
            @RequestParam(value = "max_price", defaultValue = "") Double maxPrice,
            @RequestParam(value = "sortByPrice", defaultValue = "") String sortByPrice,
            @RequestParam(value = "sortByQuantity", defaultValue = "") String sortByQuantity,
            @RequestParam(value = "sortByNumOfPage", defaultValue = "") String sortByNumOfPage,
            @RequestParam("search") String searchValue,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage<>(true, HttpStatus.OK.value(),customBookRepository.findAllBookByFilter(categoryId, languageId, publisherId, minPrice, maxPrice, searchValue,sortByPrice, sortByQuantity,sortByNumOfPage, PageRequest.of(page, size))));
    }

    @PostMapping(value = "/create-book", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> upload(@Valid @ModelAttribute BookCreateRequest bookCreateRequest) {
        bookService.addBook(bookCreateRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/create-csv-book", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public  ResponseEntity<Void> csvBook(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        bookService.addBooksFromCsvFile(multipartFile);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/edit-book/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> edit(@Valid @ModelAttribute BookCreateRequest bookCreateRequest, @PathVariable Long id) {
        bookService.editBook(bookCreateRequest, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/delete-book/{id}")
    public ResponseEntity<Void> edit(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/create-csv-book")
    public ResponseEntity<Void> createCsvBook(@RequestParam MultipartFile multipartFile) {

        return ResponseEntity.noContent().build();
    }
}
