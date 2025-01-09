package io.github.api.validator;

import io.github.api.domain.User;
import io.github.api.domain.exceptions.ObjectDuplicateException;
import io.github.api.repositories.UserModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserModelRepository repository;

    public void validate(User user) {
        if (existsUserWithEmail(user)) {
            throw new ObjectDuplicateException("User already registered");
        }
    }

    private boolean existsUserWithEmail(User user) {
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
}
