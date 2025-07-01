package com.amigopay.user.user.validation.rule.create;

import com.amigopay.user.user.dto.CreateUserRequest;
import com.amigopay.user.user.validation.utils.sanitizer.UserSanitizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SanitizeUserDataValidator implements UserCreateValidationRule {

    private final UserSanitizer userSanitizer;

    @Override
    public void validate(CreateUserRequest request) {
        userSanitizer.sanitizeInPlace(request);
    }
}
