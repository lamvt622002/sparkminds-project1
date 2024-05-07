package com.example.project1.annotations.checker;

import com.example.project1.annotations.PasswordMatches;
import com.example.project1.payload.request.RegisterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesChecker implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        RegisterRequest  registerRequest = (RegisterRequest) o;

        return registerRequest.getPassword().equals(registerRequest.getConfirmPassword());
    }
}
