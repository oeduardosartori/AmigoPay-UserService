package com.amigopay.user.user.validation.rule.update;

import com.amigopay.user.user.dto.UpdateUserRequest;
import com.amigopay.user.user.validation.utils.sanitizer.UserSanitizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SanitizeUserDataUpdateValidator implements UserUpdateValidationRule{

    private final UserSanitizer userSanitizer;

    @Override
    public void validate(UpdateUserRequest request, UUID userId) {
        userSanitizer.sanitizeInPlace(request);
    }
}
