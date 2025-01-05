package io.github.api.domain.mapper;

import io.github.api.domain.UserModel;
import io.github.api.domain.dto.UserModelRequestDTO;
import io.github.api.domain.dto.UserModelResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserModelMapper {

    UserModel toEntity(UserModelRequestDTO dto);

    UserModelResponseDTO toDTO(UserModel userModel);
}
