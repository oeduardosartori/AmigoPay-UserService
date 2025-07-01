package com.amigopay.user.user.validation;

import com.amigopay.user.user.validation.rule.create.UserCreateValidationRule;
import com.amigopay.user.user.validation.rule.update.UserUpdateValidationRule;
import com.amigopay.user.user.dto.CreateUserRequest;
import com.amigopay.user.user.dto.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final List<UserCreateValidationRule> createRules;
    private final List<UserUpdateValidationRule> updateRules;

    public void validateCreateUser(CreateUserRequest request) {
        createRules.forEach(rule -> rule.validate(request));
    }

    public void validateUpdateUser(UpdateUserRequest request, UUID userId) {
        updateRules.forEach(rule -> rule.validate(request, userId));
    }
}
