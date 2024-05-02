package com.example.project1.payload.request;

import com.example.project1.annotations.ValidChangePassword;
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
@ValidChangePassword(message = "{error.password.matches}")
public class ChangePasswordRequest {
    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "{error.password.invalid}")
    private String oldPassword;

    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "{error.password.invalid}")
    private String newPassword;

    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "{error.password.invalid}")
    private String confirmNewPassword;
}
