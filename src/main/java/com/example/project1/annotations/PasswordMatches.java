package com.example.project1.annotations;

import com.example.project1.annotations.checker.PasswordMatchesChecker;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesChecker.class)
public @interface PasswordMatches {
    String message() default "Password and confirm password must be the same";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
