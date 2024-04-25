package com.example.project1.payload.request;

import com.example.project1.constants.Constants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "Password must have at least 6 characters, contain at least 1 lowercase letter, 1 uppercase letter and 1 special character")
    private String oldPassword;

    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "Password must have at least 6 characters, contain at least 1 lowercase letter, 1 uppercase letter and 1 special character")
    private String newPassword;

    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "Password must have at least 6 characters, contain at least 1 lowercase letter, 1 uppercase letter and 1 special character")
    private String confirmNewPassword;
}
