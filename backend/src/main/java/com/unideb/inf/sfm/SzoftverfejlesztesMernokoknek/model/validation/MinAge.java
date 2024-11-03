package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = AgeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MinAge {
    String message() default "Player must be at least 14 years old.";
    int value() default 10;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
