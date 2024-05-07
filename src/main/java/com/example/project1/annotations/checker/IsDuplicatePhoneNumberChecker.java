package com.example.project1.annotations.checker;

import com.example.project1.annotations.IsDuplicatePhoneNumber;
import com.example.project1.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IsDuplicatePhoneNumberChecker implements ConstraintValidator<IsDuplicatePhoneNumber, String> {
    private final UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        return !userRepository.existsByPhoneNumber(value);
    }
}
