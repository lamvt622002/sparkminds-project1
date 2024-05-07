package com.example.project1.payload.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class AuthorResponse {
    private Long id;
    private String fullName;
    private LocalDate birthDay;
}
