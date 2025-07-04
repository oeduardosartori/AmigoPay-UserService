package com.amigopay.user.user.service.impl;

import com.amigopay.user.common.enums.ValidationMessage;
import com.amigopay.user.common.util.MessageResolver;
import com.amigopay.user.exception.BusinessException;
import com.amigopay.events.UserCreatedEvent;
import com.amigopay.user.messaging.publisher.UserEventPublisher;
import com.amigopay.user.user.dto.CreateUserRequest;
import com.amigopay.user.user.dto.UpdateUserRequest;
import com.amigopay.user.user.dto.UserResponse;
import com.amigopay.user.user.entity.User;
import com.amigopay.user.user.mapper.UserMapper;
import com.amigopay.user.user.repository.UserRepository;
import com.amigopay.user.user.validation.UserValidator;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserValidator userValidator;

    @Mock
    private MessageResolver messageResolver;

    @Mock
    private UserEventPublisher eventPublisher;

    private User user;
    private UUID userId;
    private CreateUserRequest createUserRequest;
    private UpdateUserRequest updateUserRequest;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();

        createUserRequest = CreateUserRequest.builder()
                .firstName("Eduardo")
                .lastName("Sartori")
                .birthDate(LocalDate.of(2000, 4, 10))
                .cpf("123.456.789-00")
                .email("eduardosartori@email.com")
                .password("Senh@123")
                .build();

        updateUserRequest = UpdateUserRequest.builder()
                .firstName("Eduardo")
                .lastName("Sartori")
                .birthDate(LocalDate.of(2000, 4, 10))
                .cpf("123.456.789-00")
                .email("eduardosartori@email.com")
                .build();

        user = User.builder()
                .id(userId)
                .firstName("Eduardo")
                .lastName("Sartori")
                .email("eduardosartori@email.com")
                .cpf("123.456.789-00")
                .birthDate(LocalDate.of(2000, 4, 10))
                .passwordHash("hashedPassword")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userResponse = UserResponse.builder()
                .id(userId)
                .firstName("Eduardo")
                .lastName("Sartori")
                .email("eduardosartori@email.com")
                .cpf("123.456.789-00")
                .birthDate(LocalDate.of(2000, 4, 10))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Should successfully create a user, hash the password, save it, and publish a creation event")
    void shouldCreateUserSuccessfully() {
        // given
        given(userMapper.toEntity(createUserRequest)).willReturn(user);
        given(passwordEncoder.encode("Senh@123")).willReturn("hashedPassword");
        given(userRepository.save(any(User.class))).willReturn(user);
        given(userMapper.toResponse(user)).willReturn(userResponse);

        // when
        UserResponse result = userService.createUser(createUserRequest);

        // then
        then(userValidator).should().validateCreateUser(createUserRequest);
        then(userRepository).should().save(user);
        then(eventPublisher).should().publishUserCreated(any(User.class));
        assertThat(result).isEqualTo(userResponse);
    }

    @Test
    @DisplayName("Should return all users with pagination applied")
    void shouldReturnAllUsersPaged() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> userPage = new PageImpl<>(List.of(user));
        given(userRepository.findAll(pageable)).willReturn(userPage);
        given(userMapper.toResponse(user)).willReturn(userResponse);

        // when
        Page<UserResponse> result = userService.getAllUsers(pageable);

        // then
        assertThat(result.getContent())
                .asInstanceOf(InstanceOfAssertFactories.list(UserResponse.class))
                .hasSize(1)
                .containsExactly(userResponse);
    }

    @Test
    @DisplayName("Should throw BusinessException when user is not found by ID")
    void shouldThrowExceptionWhenUserNotFoundById() {
        // given
        given(userRepository.findById(userId)).willReturn(Optional.empty());
        given(messageResolver.resolve(ValidationMessage.USER_NOT_FOUND)).willReturn("User not found");

        // when / then
        assertThatThrownBy(() -> userService.getUserById(userId))
                .isInstanceOf(BusinessException.class)
                .hasMessage("User not found");
    }

    @Test
    @DisplayName("Should return user by ID when user exists")
    void shouldReturnUserById() {
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(userMapper.toResponse(user)).willReturn(userResponse);

        UserResponse result = userService.getUserById(userId);

        assertThat(result).isEqualTo(userResponse);
    }

    @Test
    @DisplayName("Should update user successfully when user exists")
    void shouldUpdateUserSuccessfully() {
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(userRepository.save(user)).willReturn(user);
        given(userMapper.toResponse(user)).willReturn(userResponse);

        UserResponse result = userService.updateUser(userId, updateUserRequest);

        then(userValidator).should().validateUpdateUser(updateUserRequest, userId);
        then(userMapper).should().updateUserFromDto(updateUserRequest, user);
        assertThat(result).isEqualTo(userResponse);
    }

    @Test
    @DisplayName("Should throw BusinessException when updating a non-existent user")
    void shouldThrowExceptionWhenUpdatingNonExistentUser() {
        given(userRepository.findById(userId)).willReturn(Optional.empty());
        given(messageResolver.resolve(ValidationMessage.USER_NOT_FOUND)).willReturn("User not found");

        assertThatThrownBy(() -> userService.updateUser(userId, updateUserRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessage("User not found");
    }

    @Test
    @DisplayName("Should delete user successfully when user exists")
    void shouldDeleteUserSuccessfully() {
        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        userService.deleteUser(userId);

        then(userRepository).should().delete(user);
    }

    @Test
    @DisplayName("Should throw BusinessException when deleting a non-existent user")
    void shouldThrowExceptionWhenDeletingNonExistentUser() {
        given(userRepository.findById(userId)).willReturn(Optional.empty());
        given(messageResolver.resolve(ValidationMessage.USER_NOT_FOUND)).willReturn("User not found");

        assertThatThrownBy(() -> userService.deleteUser(userId))
                .isInstanceOf(BusinessException.class)
                .hasMessage("User not found");
    }

    @Test
    @DisplayName("Should encode user password before saving user to repository")
    void shouldEncodePasswordBeforeSavingUser() {
        // given
        given(userMapper.toEntity(createUserRequest)).willReturn(user);
        given(passwordEncoder.encode(createUserRequest.getPassword())).willReturn("hashedPassword");
        given(userRepository.save(any(User.class))).willReturn(user);
        given(userMapper.toResponse(user)).willReturn(userResponse);

        // when
        userService.createUser(createUserRequest);

        // then
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        then(userRepository).should().save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertThat(savedUser.getPasswordHash()).isEqualTo("hashedPassword");
    }

    @Test
    @DisplayName("Should publish UserCreatedEvent with correct data after user creation")
    void shouldPublishCorrectUserCreatedEvent() {
        // given
        given(userMapper.toEntity(createUserRequest)).willReturn(user);
        given(passwordEncoder.encode(createUserRequest.getPassword())).willReturn("hashedPassword");
        given(userRepository.save(any(User.class))).willReturn(user);
        given(userMapper.toResponse(user)).willReturn(userResponse);

        // when
        userService.createUser(createUserRequest);

        // then
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        then(eventPublisher).should().publishUserCreated(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assertThat(capturedUser.getId()).isEqualTo(user.getId());
        assertThat(capturedUser.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(capturedUser.getLastName()).isEqualTo(user.getLastName());
        assertThat(capturedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(capturedUser.getCreatedAt()).isEqualTo(user.getCreatedAt());
    }

    @Test
    @DisplayName("Should call userValidator.validateCreateUser with correct data")
    void shouldCallValidateCreateUser() {
        // given
        given(userMapper.toEntity(createUserRequest)).willReturn(user);
        given(passwordEncoder.encode(createUserRequest.getPassword())).willReturn("hashedPassword");
        given(userRepository.save(any(User.class))).willReturn(user);
        given(userMapper.toResponse(user)).willReturn(userResponse);

        // when
        userService.createUser(createUserRequest);

        // then
        then(userValidator).should().validateCreateUser(createUserRequest);
    }
}
