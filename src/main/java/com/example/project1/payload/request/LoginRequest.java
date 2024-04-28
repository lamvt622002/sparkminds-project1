package com.example.project1.payload.request;

import com.example.project1.constants.Constants;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @Pattern(regexp = Constants.EMAIL_REGEX, message = "{error.email.invalid}")
    private String email;

    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "{error.password.invalid}")
    private String password;
}
