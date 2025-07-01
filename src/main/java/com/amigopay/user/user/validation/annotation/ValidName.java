package com.amigopay.user.user.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidName {
    String message() default "{validation.name.valid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
