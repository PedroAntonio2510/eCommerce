package io.github.api.domain.dto;

public record UserResponseDTO(
        String name,
        String lastName,
        String phoneNumber,
        String cpf,
        String email
) {
}
