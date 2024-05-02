package com.example.project1.annotations.checker;

import com.example.project1.annotations.IsDuplicateEmail;
import com.example.project1.constants.Constants;
import com.example.project1.exception.DataIntegrityViolationException;
import com.example.project1.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IsDuplicateEmailChecker implements ConstraintValidator<IsDuplicateEmail, String> {
    private final UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !userRepository.existsByEmail(value);
    }
}
