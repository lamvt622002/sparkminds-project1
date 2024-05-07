package com.example.project1.annotations.checker;

import com.example.project1.annotations.ValidChangeEmail;
import com.example.project1.constants.Constants;
import com.example.project1.exception.BadRequestException;
import com.example.project1.payload.request.ChangeEmailRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@AllArgsConstructor
public class ValidChangeEmailChecker implements ConstraintValidator<ValidChangeEmail, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        ChangeEmailRequest changeEmailRequest = (ChangeEmailRequest) o;

        return !email.equals(changeEmailRequest.getNewEmail());
    }
}
