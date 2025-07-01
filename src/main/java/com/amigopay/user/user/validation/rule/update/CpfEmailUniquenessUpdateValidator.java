package com.amigopay.user.user.validation.rule.update;

import com.amigopay.user.common.enums.ValidationMessage;
import com.amigopay.user.common.util.MessageResolver;
import com.amigopay.user.exception.BusinessException;
import com.amigopay.user.user.dto.UpdateUserRequest;
import com.amigopay.user.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CpfEmailUniquenessUpdateValidator implements UserUpdateValidationRule{

    private final UserRepository userRepository;
    private final MessageResolver messageResolver;

    @Override
    public void validate(UpdateUserRequest request, UUID userId) {
        userRepository.findByEmail(request.getEmail())
                .filter(user -> !user.getId().equals(userId))
                .ifPresent(user -> {
                    throw new BusinessException(messageResolver.resolve(ValidationMessage.EMAIL_ALREADY_EXISTS));
                });

        userRepository.findByCpf(request.getCpf())
                .filter(user -> !user.getId().equals(userId))
                .ifPresent(user -> {
                    throw new BusinessException(messageResolver.resolve(ValidationMessage.CPF_ALREADY_EXISTS));
                });
    }
}
