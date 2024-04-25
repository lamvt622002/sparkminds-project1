package com.example.project1.payload.request;

import com.example.project1.constants.Constants;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePhoneRequest {
    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "Password must have at least 6 characters, contain at least 1 lowercase letter, 1 uppercase letter and 1 special character")
    private String password;

    @Size(min = 10, max = 10, message = "Phone number must be 10 numeric")
    @Pattern(regexp = Constants.PHONE_NUMBER_REGEX, message = "Phone number is invalid")
    private String phoneNumber;
}
