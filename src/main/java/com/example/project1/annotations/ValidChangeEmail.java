package com.example.project1.annotations;

import com.example.project1.annotations.checker.ValidChangeEmailChecker;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidChangeEmailChecker.class)
public @interface ValidChangeEmail {
    String message() default "New email and old email must be different";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
