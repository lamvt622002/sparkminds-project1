package com.example.project1.payload.dto;

import com.example.project1.entities.Author;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BookDto {
    private Long id;
    private String title;
    private String language;
    private String category;
    private String publisher;
    private Double price;
    private Integer numOfPages;
    private String description;
    private Integer quantity;
    private String image;
    private List<String> authorsFirstName;
}
