package com.example.project1.payload.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BookLanguageResponse {
    private Long id;

    private String languageName;
}
