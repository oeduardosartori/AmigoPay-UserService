package com.amigopay.user.common.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "{validation.password.valid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
