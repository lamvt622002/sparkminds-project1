package com.example.project1.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "book_language")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookLanguage extends AuditTable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @NonNull
    private String languageName;

    @OneToMany(mappedBy = "language")
    private List<Book> books;
}
