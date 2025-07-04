package com.amigopay.user.user.service.impl;

import com.amigopay.user.common.enums.ValidationMessage;
import com.amigopay.user.common.util.MessageResolver;
import com.amigopay.user.exception.BusinessException;
import com.amigopay.user.messaging.publisher.UserEventPublisher;
import com.amigopay.user.user.dto.CreateUserRequest;
import com.amigopay.user.user.dto.UpdateUserRequest;
import com.amigopay.user.user.dto.UserResponse;
import com.amigopay.user.user.entity.User;
import com.amigopay.user.user.mapper.UserMapper;
import com.amigopay.user.user.repository.UserRepository;
import com.amigopay.user.user.service.UserService;
import com.amigopay.user.user.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;
    private final MessageResolver messageResolver;
    private final UserEventPublisher eventPublisher;

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        userValidator.validateCreateUser(request);

        User user = userMapper.toEntity(request);

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setPasswordHash(hashedPassword);

        User savedUser = userRepository.save(user);
        eventPublisher.publishUserCreated(savedUser);

        return userMapper.toResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(userMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        messageResolver.resolve(ValidationMessage.USER_NOT_FOUND)
                ));

        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse updateUser(UUID id, UpdateUserRequest request) {
        userValidator.validateUpdateUser(request, id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        messageResolver.resolve(ValidationMessage.USER_NOT_FOUND)
                ));

        userMapper.updateUserFromDto(request, user);

        User updatedUser = userRepository.save(user);
        return userMapper.toResponse(updatedUser);
    }

    @Override
    public void deleteUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        messageResolver.resolve(ValidationMessage.USER_NOT_FOUND)
                ));

        userRepository.delete(user);
    }
}
