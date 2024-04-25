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
    @Pattern(regexp = Constants.EMAIL_REGEX, message = "Email is invalid")
    private String email;

    @Size(min = 6, max = 6, message = "The OTP must be 6 characters.")
    private String otp;
}
