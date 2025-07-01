package com.amigopay.user.user.validation.rule.update;

import com.amigopay.user.user.dto.UpdateUserRequest;

import java.util.UUID;

public interface UserUpdateValidationRule {
    void validate(UpdateUserRequest request, UUID userId);
}


