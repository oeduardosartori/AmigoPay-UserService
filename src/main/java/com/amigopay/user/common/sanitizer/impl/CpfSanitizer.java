package com.amigopay.user.common.sanitizer.impl;

import com.amigopay.user.common.sanitizer.Sanitizer;
import org.springframework.stereotype.Component;

@Component
public class CpfSanitizer implements Sanitizer<String> {

    @Override
    public String sanitizer(String cpf) {
        if (cpf == null) return null;
        return cpf.replaceAll("\\D", "");
    }
}

