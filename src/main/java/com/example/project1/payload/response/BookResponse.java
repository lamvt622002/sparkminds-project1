package com.example.project1.payload.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BookResponse {
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
    private List<String> authorName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
