package com.amigopay.user.user.validation.utils.sanitizer;

import com.amigopay.user.user.validation.utils.sanitizer.impl.CpfSanitizer;
import com.amigopay.user.user.validation.utils.sanitizer.impl.EmailSanitizer;
import com.amigopay.user.user.validation.utils.sanitizer.impl.NameSanitizer;
import com.amigopay.user.user.dto.CreateUserRequest;
import com.amigopay.user.user.dto.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSanitizer {

    private final NameSanitizer nameSanitizer;
    private final EmailSanitizer emailSanitizer;
    private final CpfSanitizer cpfSanitizer;

    public void sanitizeInPlace(CreateUserRequest request) {
        request.setFirstName(nameSanitizer.sanitizer(request.getFirstName()));
        request.setLastName(nameSanitizer.sanitizer(request.getLastName()));
        request.setEmail(emailSanitizer.sanitizer(request.getEmail()));
        request.setCpf(cpfSanitizer.sanitizer(request.getCpf()));
    }

    public void sanitizeInPlace(UpdateUserRequest request) {
        request.setFirstName(nameSanitizer.sanitizer(request.getFirstName()));
        request.setLastName(nameSanitizer.sanitizer(request.getLastName()));
        request.setEmail(emailSanitizer.sanitizer(request.getEmail()));
        request.setCpf(cpfSanitizer.sanitizer(request.getCpf()));
    }
}


