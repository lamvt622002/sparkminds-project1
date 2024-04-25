package com.example.project1.payload.request;

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
public class ChangePasswordWithoutAuthRequest {

    @Pattern(regexp = Constants.EMAIL_REGEX, message = "Email is invalid")
    private String email;

    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "Password must have at least 6 characters, contain at least 1 lowercase letter, 1 uppercase letter and 1 special character")
    private String oldPassword;

    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "Password must have at least 6 characters, contain at least 1 lowercase letter, 1 uppercase letter and 1 special character")
    private String newPassword;
}
