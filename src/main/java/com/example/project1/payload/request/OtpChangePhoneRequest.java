package com.example.project1.payload.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OtpChangePhoneRequest {
    @Size(min = 6, max = 6, message = "{error.otp.length}")
    private String otp;
}
