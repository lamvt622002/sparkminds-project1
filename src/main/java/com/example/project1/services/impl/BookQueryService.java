package com.example.project1.services.impl;

import com.example.project1.entities.*;
import com.example.project1.mapper.BookMapper;
import com.example.project1.payload.response.BookResponse;
import com.example.project1.repository.BookRepository;
import com.example.project1.services.criteria.BookCriteria;
import com.example.project1.services.criteria.TimeCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.jhipster.service.QueryService;

@Service
@RequiredArgsConstructor
public class BookQueryService extends QueryService<Book> {
    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    private final  TimeQueryService timeQueryService;

    public Page<BookResponse> findByCriteria (BookCriteria bookCriteria, TimeCriteria timeCriteria, Pageable pageable){
        final Specification<Book> specification = createSpecification(bookCriteria).and(timeQueryService.createTimeSpecification(timeCriteria));
        Page<Book> books = bookRepository.findAll(specification, pageable);
        return books.map(bookMapper::bookToBookResponse);
    }
    protected Specification<Book> createSpecification(BookCriteria criteria) {
        Specification<Book> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategoryId(), bookRoot -> bookRoot.join(Book_.CATEGORY).get(Category_.ID)));
            }
            if (criteria.getLanguageId() != null) {
                specification = specification.and(buildSpecification(criteria.getLanguageId(), bookRoot -> bookRoot.join(Book_.LANGUAGE).get(BookLanguage_.ID)));
            }
            if (criteria.getPublisherId() != null) {
                specification = specification.and(buildSpecification(criteria.getPublisherId(), bookRoot -> bookRoot.join(Book_.PUBLISHER).get(Publisher_.ID)));
            }
            if(criteria.getAuthorId() != null) {
                specification = specification.and(buildSpecification(criteria.getAuthorId(), bookRoot -> bookRoot.join(Book_.AUTHORS).get(Author_.ID)));
            }
            if(criteria.getSearch() != null){
                String searchKeyword = "%" + criteria.getSearch().getEquals() + "%";
                specification = specification.and(
                        (root, query, criteriaBuilder) -> criteriaBuilder.or(
                                buildStringSpecification(criteria.getSearch(), Book_.title).toPredicate(root, query, criteriaBuilder),
                                buildStringSpecification(criteria.getSearch(), Book_.description).toPredicate(root, query, criteriaBuilder),
                                criteriaBuilder.like(root.join(Book_.authors).get(Author_.FIRST_NAME), searchKeyword),
                                criteriaBuilder.like(root.join(Book_.category).get(Category_.CATEGORY_NAME), searchKeyword),
                                criteriaBuilder.like(root.join(Book_.language).get(BookLanguage_.LANGUAGE_NAME), searchKeyword),
                                criteriaBuilder.like(root.join(Book_.publisher).get(Publisher_.PUBLISHER_NAME), searchKeyword)
                        )
                );
            }
            if(criteria.getMinPrice() != null && criteria.getMaxPrice() != null){
                specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(Book_.PRICE),criteria.getMinPrice().getEquals(), criteria.getMaxPrice().getEquals()));
            }
        }
        return specification;
    }
}
