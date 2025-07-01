package com.amigopay.user.user.validation.utils.sanitizer.impl;

import com.amigopay.user.user.validation.utils.sanitizer.Sanitizer;
import org.springframework.stereotype.Component;

@Component
public class EmailSanitizer implements Sanitizer<String> {
    @Override
    public String sanitizer(String email) {
        if (email == null) return null;
        return email.trim().toLowerCase();
    }
}
