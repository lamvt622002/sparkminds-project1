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
    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "{error.password.invalid}")
    private String password;

    @Pattern(regexp = Constants.EMAIL_REGEX, message = "{error.email.invalid }")
    private String newEmail;
}
