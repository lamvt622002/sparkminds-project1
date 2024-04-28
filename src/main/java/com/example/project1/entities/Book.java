package com.example.project1.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book extends AuditTable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @NonNull
    private String title;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private BookLanguage language;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @NonNull
    private Double price;

    @NonNull
    private Integer numOfPages;

    @NonNull
    private String description;

    @NonNull
    private Integer quantity;

    @NonNull
    private String image;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Author> authors;
}
