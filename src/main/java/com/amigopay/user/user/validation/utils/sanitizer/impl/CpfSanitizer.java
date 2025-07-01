package com.amigopay.user.user.validation.utils.sanitizer.impl;

import com.amigopay.user.user.validation.utils.sanitizer.Sanitizer;
import org.springframework.stereotype.Component;

@Component
public class CpfSanitizer implements Sanitizer<String> {

    @Override
    public String sanitizer(String cpf) {
        if (cpf == null) return null;
        return cpf.replaceAll("\\D", "");
    }
}

