package com.example.project1.payload.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDto {
    @NotNull
    private String accessToken;

    @NotNull
    private String refreshToken;

    @NotNull
    private String session;
}
