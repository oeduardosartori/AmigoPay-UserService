package com.amigopay.user.user.mapper;

import com.amigopay.user.user.dto.CreateUserRequest;
import com.amigopay.user.user.dto.UpdateUserRequest;
import com.amigopay.user.user.dto.UserResponse;
import com.amigopay.user.user.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(CreateUserRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UpdateUserRequest dto, @MappingTarget User user);

    UserResponse toResponse(User entity);
}
