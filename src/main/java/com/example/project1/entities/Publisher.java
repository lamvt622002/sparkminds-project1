package com.example.project1.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "publisher")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Publisher extends AuditTable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @NonNull
    private String publisher_name;

    @OneToMany(mappedBy = "publisher")
    private List<Book> books;
}
