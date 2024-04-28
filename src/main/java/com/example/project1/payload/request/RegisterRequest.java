package com.example.project1.payload.request;

import com.example.project1.annotations.ValidPhoneNumber;
import com.example.project1.constants.Constants;
import com.example.project1.utitilies.MessagesUtils;
import com.example.project1.utitilies.StringUtils;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotNull
    @Size(min = 2 , max = 10,message = "{error.firstname.length}") // ?
    private String firstName;

    @NotNull
    @Size(min = 2, max = 10, message = "{error.lastname.length}")
    private String lastName;

    @Past(message = "{error.birthday.past}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDay;

    @ValidPhoneNumber(message = "{error.phonenumber.invalid}")
    private String phoneNumber;

    @Pattern(regexp = Constants.EMAIL_REGEX, message = "{error.email.invalid}")
    private String email;

    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "{error.password.invalid}")
    private String password;

    @Pattern(regexp = Constants.PASSWORD_REGEX, message = "{error.password.invalid}")
    private String confirmPassword;

}
