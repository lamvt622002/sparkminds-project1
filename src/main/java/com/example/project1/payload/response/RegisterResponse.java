package com.example.project1.payload.response;

import com.example.project1.enums.Role;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {
    @NonNull
    private Long id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private LocalDate birthDay;

    @NonNull
    private String phoneNumber;

    @NonNull
    private String email;

    @NonNull
    private Integer failedLoginAttempts;

    private LocalDateTime lockoutTime;

    @NonNull
    private String role;

    @NonNull
    private Integer status;

    @NonNull
    private Integer isDelete;

    @NonNull
    private LocalDateTime createdAt;

    @NonNull
    private LocalDateTime updatedAt;

}
