package com.amigopay.user.common.sanitizer.impl;

import com.amigopay.user.common.sanitizer.Sanitizer;
import org.springframework.stereotype.Component;

@Component
public class EmailSanitizer implements Sanitizer<String> {
    @Override
    public String sanitizer(String email) {
        if (email == null) return null;
        return email.trim().toLowerCase();
    }
}
