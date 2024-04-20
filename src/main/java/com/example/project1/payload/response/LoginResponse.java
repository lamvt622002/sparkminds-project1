package com.example.project1.payload.response;

import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private String firstName;

    private String lastName;

    private String email;

    private LocalDate birthDay;

    private String phoneNumber;

    private String role;

    private String status;

    private String accessToken;

    private String refreshToken;
}
