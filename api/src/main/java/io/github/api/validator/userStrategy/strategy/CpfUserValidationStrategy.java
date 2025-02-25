package io.github.api.validator.userStrategy.strategy;

import io.github.api.domain.User;
import io.github.api.repositories.UserRepository;
import io.github.api.validator.userStrategy.UserValidationStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("cpf_validation")
@AllArgsConstructor
public class CpfUserValidationStrategy implements UserValidationStrategy {

    private final UserRepository repository;

    @Override
    public boolean isValid(User user) {
        Optional<User> userFound = repository.findByCpf(user.getCpf());

        return userFound
                .map(User::getId)
                .filter(id -> !id.equals(user.getId()))
                .isEmpty(); // Retorna true apenas se não houver conflito de CPF
    }

}
