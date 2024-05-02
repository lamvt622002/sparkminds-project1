package com.example.project1.payload.request;

import com.example.project1.annotations.ValidChangePasswordWithoutAuth;
import com.example.project1.constants.Constants;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ValidChangePasswordWithoutAuth(message = "{error.password.duplicate}")
public class ChangePasswordWithoutAuthRequest {

    @Pattern(regexp = Constants.EMAIL_REGEX, message = "{error.email.invalid}")
    private String email;

    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "{error.password.invalid}")
    private String oldPassword;

    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "{error.password.invalid}")
    private String newPassword;
}
