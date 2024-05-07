package com.example.project1.repository.specs;

import com.example.project1.entities.*;
import com.example.project1.mapper.BookMapper;
import com.example.project1.payload.response.BookResponse;
import com.example.project1.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomBookRepository {
    private final BookRepository bookRepository;

    private final BookMapper bookMapper;
    public Page<BookResponse> findAllBookNameLike(String name, Pageable pageable){
        Page<Book> books = bookRepository.findAll(nameLike(name), pageable);
        return books.map(bookMapper::bookToBookResponse);
    }

    public Page<BookResponse> findAllBook( Pageable pageable){
        Page<Book> books = bookRepository.findAll(pageable);
        return books.map(bookMapper::bookToBookResponse);
    }

    public Page<BookResponse> findAllBookByFilter(Long categoryId, Long languageId, Long publisherId, Double minPrice, Double maxPrice, String searchValue, String sortByPrice, String sortByQuantity, String sortByNumOfPage,Pageable pageable){
        Specification<Book> spec = Specification.where(checkingDelete());
        Sort sort = null;
        if(categoryId != null){
            spec = spec.and(hasCategory(categoryId));
        }
        if(languageId != null){
            spec = spec.and(hasLanguage(languageId));
        }
        if(publisherId != null){
            spec = spec.and(hasPublisher(publisherId));
        }
        if(minPrice != null && maxPrice != null){
            spec = spec.and(hasPriceRange(minPrice, maxPrice));
        }
        if(searchValue != null){
            spec = spec.and(nameLike(searchValue));
        }
        if(sortByPrice != null && !sortByPrice.isEmpty()){
            sort = sort("price");
        }
        if(sortByQuantity != null && !sortByQuantity.isEmpty()){
            sort = sort("quantity");
        }
        if(sortByNumOfPage != null && !sortByNumOfPage.isEmpty()){
            sort = sort("numOfPages");
        }
        if(sort != null){
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        }

        Page<Book> books = bookRepository.findAll(spec, pageable);
        return books.map(bookMapper::bookToBookResponse);
    }

    private Specification<Book> nameLike(String name){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                criteriaBuilder.like(root.get(Book_.TITLE), "%" + name + "%"),
                criteriaBuilder.like(root.get(Book_.DESCRIPTION), "%" + name + "%")
        );
    }

    private Specification<Book> hasCategory(Long categoryId){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Book_.CATEGORY).get(Category_.ID), categoryId);
    }

    private Specification<Book> hasLanguage(Long languageId){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Book_.LANGUAGE).get(BookLanguage_.ID), languageId);
    }

    private Specification<Book> hasPublisher(Long publisherId){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Book_.PUBLISHER).get(Publisher_.ID), publisherId);
    }

    private Specification<Book> hasPriceRange(Double minPrice, double maxPrice){
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(Book_.PRICE), minPrice, maxPrice);
    }

    private Specification<Book> checkingDelete(){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Book_.isDelete), 0);
    }

    private Sort sort(String... arguments){
        if(arguments == null || arguments.length ==  0){
            return Sort.unsorted();
        }
        List<Sort.Order> orders = new ArrayList<>();
        for (String properties : arguments) {
            orders.add(new Sort.Order(Sort.Direction.valueOf("ASC"), properties));
        }
        return Sort.by(orders);
    }
}
