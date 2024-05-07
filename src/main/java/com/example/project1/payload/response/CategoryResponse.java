package com.example.project1.payload.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CategoryResponse {
    private Long id;
    private String categoryName;
}
