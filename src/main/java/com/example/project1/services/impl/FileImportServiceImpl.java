package com.example.project1.services.impl;

import com.example.project1.constants.Constants;
import com.example.project1.entities.*;
import com.example.project1.exception.BadRequestException;
import com.example.project1.mapper.BookMapper;
import com.example.project1.repository.AuthorRepository;
import com.example.project1.repository.BookLanguageRepository;
import com.example.project1.repository.CategoryRepository;
import com.example.project1.repository.PublisherRepository;
import com.example.project1.services.FileImportService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FileImportServiceImpl implements FileImportService {
    private final BookLanguageRepository bookLanguageRepository;

    private final CategoryRepository categoryRepository;

    private final PublisherRepository publisherRepository;

    private final AuthorRepository authorRepository;

    private final BookMapper bookMapper;
    @Override
    public List<Book> readBooksFromCsv(InputStream inputStream) {
        CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim();
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            CSVParser csvParser = new CSVParser(bufferedReader, csvFormat)){
            List<Book> books = new ArrayList<>();
            List<String> headers= csvParser.getHeaderNames();
            if(!isValidHeaders(headers)){
                throw new BadRequestException("Invalid data");
            }
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for(CSVRecord csvRecord : csvRecords){
                Book book = new Book();
                BookLanguage bookLanguage = bookLanguageRepository.findById(Long.parseLong(csvRecord.get("languageId"))).orElseThrow(() -> new BadRequestException("Invalid language"));
                Category category = categoryRepository.findById(Long.parseLong(csvRecord.get("categoryId"))).orElseThrow(() -> new BadRequestException("Invalid category"));
                Publisher publisher = publisherRepository.findById(Long.parseLong(csvRecord.get("publisherId"))).orElseThrow(() -> new BadRequestException("Invalid publisher"));
                Author author = authorRepository.findById(Long.parseLong(csvRecord.get("authorId"))).orElseThrow(() -> new BadRequestException("Invalid author"));
                book.setTitle(csvRecord.get("title"));
                book.setLanguage(bookLanguage);
                book.setCategory(category);
                book.setPublisher(publisher);
                book.getAuthors().add(author);
                book.setPrice(Double.parseDouble(csvRecord.get("price")));
                book.setNumOfPages(Integer.parseInt(csvRecord.get("numOfPages")));
                book.setDescription(csvRecord.get("description"));
                book.setQuantity(Integer.parseInt(csvRecord.get("quantity")));
                book.setImage(csvRecord.get("image"));
                book.setIsDelete(Integer.parseInt(csvRecord.get("isDelete")));
                books.add(book);
            }
            return books;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void validateCsvFormat(MultipartFile multipartFile) {
        if (!Constants.TYPE_CSV.equals(multipartFile.getContentType())) {
            throw new BadRequestException("Invalid file");
        }
        if(multipartFile.getSize() > 5 * 1024 * 1024){
            throw new BadRequestException("File must less than 5MB");
        }
    }

    public static boolean isValidHeaders(List<String> headers){
        if(headers == null || headers.size() <= 0){
            return false;
        }
        Set<String> expectedHeaders = new HashSet<>(Arrays.asList(Constants.BOOK_HEADER));
        Set<String> currentHeaders = new HashSet<>(headers);
        return expectedHeaders.equals(currentHeaders);
    }
}
