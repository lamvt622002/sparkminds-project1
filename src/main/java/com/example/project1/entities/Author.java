package com.example.project1.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "author")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author extends AuditTable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @NonNull
    private String lastName;

    @NonNull
    private String firstName;

    @NonNull
    private LocalDate birthDate;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books;
}
