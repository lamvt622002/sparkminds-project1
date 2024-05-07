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
public class GoogleValidateCodeRequest {
    @Pattern(regexp = Constants.EMAIL_REGEX, message = "{error.email.invalid}")
    private String userName;

    private Integer code;
}
