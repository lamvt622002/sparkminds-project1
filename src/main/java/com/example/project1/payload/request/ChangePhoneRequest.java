package com.example.project1.payload.request;

import com.example.project1.annotations.IsDuplicatePhoneNumber;
import com.example.project1.annotations.ValidPhoneNumber;
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
    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "{error.password.invalid}")
    private String password;

    @ValidPhoneNumber(message = "{error.phonenumber.invalid}")
    @IsDuplicatePhoneNumber(message = "{error.phonenumber.duplicate}")
    private String phoneNumber;
}
