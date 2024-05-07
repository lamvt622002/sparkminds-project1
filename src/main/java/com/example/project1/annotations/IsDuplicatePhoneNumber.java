package com.example.project1.annotations;

import com.example.project1.annotations.checker.IsDuplicatePhoneNumberChecker;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsDuplicatePhoneNumberChecker.class)
public @interface IsDuplicatePhoneNumber {
    String message() default "Phone number already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
