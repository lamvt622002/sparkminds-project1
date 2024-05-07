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
public class OtpRequest {
    @Pattern(regexp = Constants.EMAIL_REGEX, message = "{error.email.invalid}")
    private String email;

    @Size(min = 6, max = 6, message = "{error.otp.length}")
    private String otp;
}
