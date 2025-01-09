package io.github.api.domain.mapper;

import io.github.api.domain.User;
import io.github.api.domain.dto.UserRequestDTO;
import io.github.api.domain.dto.UserResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserModelMapper {

    User toEntity(UserRequestDTO dto);

    UserResponseDTO toDTO(User user);
}
