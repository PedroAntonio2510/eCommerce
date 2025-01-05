package io.github.api.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

public record UserModelRequestDTO(
        @NotBlank(message = "You must have a login name")
        String login,

        @Email(message = "The email must be valid")
                @NotBlank(message = "You must provide a email")
        String email,

        @NotBlank(message = "you must provide a password")
        String password,

        @NotBlank(message = "You must provide a nme")
        String name,

        @NotBlank(message = "you must provide a last name")
        String lastName,

        @NotBlank(message = "You must provide a phone number")
        String phoneNumber,

        @CPF
        @NotBlank(message = "you must provide a cpf")
        String cpf,

        List<String> roles
) {
}
