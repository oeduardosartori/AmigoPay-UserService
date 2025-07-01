package com.amigopay.user.user.validation.rule.create;

import com.amigopay.user.common.enums.ValidationMessage;
import com.amigopay.user.common.util.MessageResolver;
import com.amigopay.user.exception.BusinessException;
import com.amigopay.user.user.dto.CreateUserRequest;
import com.amigopay.user.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CpfEmailUniquenessValidator implements UserCreateValidationRule {

    private final UserRepository userRepository;
    private final MessageResolver messageResolver;

    @Override
    public void validate(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(messageResolver.resolve(ValidationMessage.EMAIL_ALREADY_EXISTS));
        }

        if (userRepository.existsByCpf(request.getCpf())) {
            throw new BusinessException(messageResolver.resolve(ValidationMessage.CPF_ALREADY_EXISTS));
        }
    }
}
