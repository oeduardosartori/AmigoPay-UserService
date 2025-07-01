package com.amigopay.user.common.sanitizer.impl;

import com.amigopay.user.common.sanitizer.Sanitizer;
import org.springframework.stereotype.Component;

@Component
public class NameSanitizer implements Sanitizer<String> {

    @Override
    public String sanitizer(String name) {
        if (name == null) return null;
        return name.trim().replaceAll("\\s{2,}", " ");
    }
}
