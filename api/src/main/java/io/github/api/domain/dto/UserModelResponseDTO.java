package io.github.api.domain.dto;

public record UserModelResponseDTO(
        String name,
        String lastName,
        String phoneNumber,
        String cpf
) {
}
