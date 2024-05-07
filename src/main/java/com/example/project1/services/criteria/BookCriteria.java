package com.example.project1.services.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class BookCriteria implements Serializable, Criteria {
    private LongFilter categoryId;
    private LongFilter languageId;
    private LongFilter  publisherId;
    private LongFilter authorId;
    private DoubleFilter minPrice;
    private DoubleFilter maxPrice;
    private StringFilter search;

    public BookCriteria(BookCriteria other){
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
        this.languageId = other.languageId == null ? null: other.languageId.copy();
        this.publisherId = other.publisherId == null ? null : other.publisherId.copy();
        this.authorId = other.authorId == null ? null : other.publisherId.copy();
        this.minPrice = other.minPrice == null ? null : other.minPrice.copy();
        this.maxPrice = other.maxPrice == null ? null : other.maxPrice.copy();
        this.search  =  other.search ==  null ? null : other.search.copy();
    }

    @Override
    public Criteria copy() {
        return new BookCriteria(this);
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }
        final BookCriteria that = (BookCriteria) o;
        return (Objects.equals(
                categoryId, that.categoryId)
                && Objects.equals(languageId, that.languageId)
                && Objects.equals(publisherId, that.publisherId)
                && Objects.equals(authorId, that.authorId)
                && Objects.equals(minPrice, that.minPrice)
                && Objects.equals(maxPrice, that.maxPrice)
                && Objects.equals(search, that.search));
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, languageId, publisherId, authorId, minPrice, maxPrice, search);
    }

    @Override
    public String toString() {
        return "BookCriteria{" +
                "categoryId=" + categoryId +
                ", languageId=" + languageId +
                ", publisherId=" + publisherId +
                ", authorId=" + authorId +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", search=" + search +
                '}';
    }
}
