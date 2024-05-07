package com.example.project1.payload.request;

import com.example.project1.annotations.IsDuplicateEmail;
import com.example.project1.annotations.ValidChangeEmail;
import com.example.project1.constants.Constants;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@ValidChangeEmail(message = "{error.email.duplicate_old_and_new_email}")
public class ChangeEmailRequest {
    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "{error.password.invalid}")
    private String password;

    @Pattern(regexp = Constants.EMAIL_REGEX, message = "{error.email.invalid }")
    @IsDuplicateEmail(message = "{error.email.duplicate}")
    private String newEmail;
}
