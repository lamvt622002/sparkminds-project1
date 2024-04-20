package com.example.project1.payload.request;

import com.example.project1.constants.Constants;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotNull
    @Size(min = 2 , message = "First name must be from 2 characters")
    private String firstName;

    @NotNull
    @Size(min = 2, message = "Last name must be from 2 characters")
    private String lastName;

    @Past(message = "Birthday must be in the past")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDay;

    @Size(min = 10, max = 10, message = "Phone number must be 10 numeric")
    @Pattern(regexp = Constants.PHONE_NUMBER_REGEX, message = "Phone number is invalid")
    private String phoneNumber;

    @Pattern(regexp = Constants.EMAIL_REGEX, message = "Email is invalid")
    private String email;

    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "Password must have at least 6 characters, contain at least 1 lowercase letter, 1 uppercase letter and 1 special character")
    private String password;

    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "Password must have at least 6 characters, contain at least 1 lowercase letter, 1 uppercase letter and 1 special character")
    private String confirmPassword;

}
