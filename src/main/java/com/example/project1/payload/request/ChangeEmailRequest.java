package com.example.project1.payload.request;

import com.example.project1.constants.Constants;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChangeEmailRequest {
    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "Password must have at least 6 characters, contain at least 1 lowercase letter, 1 uppercase letter and 1 special character")
    private String password;

    @Pattern(regexp = Constants.EMAIL_REGEX, message = "Email is invalid")
    private String newEmail;
}
