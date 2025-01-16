package io.github.api.validator;

import io.github.api.domain.User;
import io.github.api.domain.exceptions.ObjectDuplicateException;
import io.github.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository repository;

    public void validate(User user) {
        if (existsUserWithCPf(user)) {
            throw new ObjectDuplicateException("This user have a registry with this CPF");
        }
        if (existirUserWithEmail(user)) {
            throw new ObjectDuplicateException("This user have a registry with this email");
        }
    }

    private boolean existsUserWithCPf(User user) {
        Optional<User> userFound = repository
                .findByCpf(user.getCpf());
        if  (user.getEmail() == null) {
            return userFound.isPresent();
        }
        return userFound
                .map(User::getId)
                .stream()
                .anyMatch(id -> !id.equals(user.getId()));
    }

    private boolean existirUserWithEmail(User user) {
        User userFound = repository.findByEmail(user.getEmail());
        return userFound != null;
    }
}
