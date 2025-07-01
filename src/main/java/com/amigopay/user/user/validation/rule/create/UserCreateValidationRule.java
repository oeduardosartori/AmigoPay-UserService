package com.amigopay.user.user.validation.rule.create;

import com.amigopay.user.user.dto.CreateUserRequest;

public interface UserCreateValidationRule {
    void validate(CreateUserRequest request);
}


