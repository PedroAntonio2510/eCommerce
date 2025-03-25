package io.github.api.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

public record UserRequestDTO(
        @NotBlank(message = "You must have a login name")
        @NotNull
        String login,

        @Email(message = "The email must be valid")
                @NotBlank(message = "You must provide a email")
        @NotNull
        String email,

        @NotBlank(message = "you must provide a password")
        @NotNull
        String password,

        @NotBlank(message = "you must provide a password")
        @NotNull
        String name,

        @NotBlank(message = "you must provide a password")
        @NotNull
        String lastName,

        @NotBlank
        @NotNull
        String phoneNumber,

        @CPF
        @NotBlank
        @NotNull
        String cpf,

        List<String> roles
) {
}
