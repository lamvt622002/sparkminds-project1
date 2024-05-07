package com.example.project1.annotations.checker;

import com.example.project1.annotations.ValidChangePassword;
import com.example.project1.payload.request.ChangePasswordRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidChangePasswordChecker implements ConstraintValidator<ValidChangePassword, Object> {
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        ChangePasswordRequest changePasswordRequest = (ChangePasswordRequest) o;

        return !changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmNewPassword());
    }
}
