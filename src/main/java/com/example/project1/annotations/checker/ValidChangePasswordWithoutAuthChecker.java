package com.example.project1.annotations.checker;

import com.example.project1.annotations.ValidChangePasswordWithoutAuth;
import com.example.project1.payload.request.ChangePasswordWithoutAuthRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidChangePasswordWithoutAuthChecker implements ConstraintValidator<ValidChangePasswordWithoutAuth, Object> {
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        ChangePasswordWithoutAuthRequest changePasswordWithoutAuthRequest = (ChangePasswordWithoutAuthRequest) o;

        return !changePasswordWithoutAuthRequest.getOldPassword().equals(changePasswordWithoutAuthRequest.getNewPassword());
    }
}
