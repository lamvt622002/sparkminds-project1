package com.example.project1.annotations;

import com.example.project1.annotations.checker.ValidChangePasswordChecker;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidChangePasswordChecker.class)
public @interface ValidChangePassword {
    String message() default "New password and confirm new password must be the same";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
