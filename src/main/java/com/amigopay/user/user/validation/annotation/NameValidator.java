package com.amigopay.user.user.validation.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<ValidName, String> {

    private static final String NAME_PATTERN = "^[A-Za-zÀ-ÖØ-öø-ÿ ]+$";

    private Pattern pattern;

    @Override
    public void initialize(ValidName constraintAnnotation) {
        pattern = Pattern.compile(NAME_PATTERN);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return pattern.matcher(value).matches();
    }
}
