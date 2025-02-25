package io.github.api.validator.userStrategy.strategy;

import io.github.api.domain.User;
import io.github.api.repositories.UserRepository;
import io.github.api.validator.userStrategy.UserValidationStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component("email_validation")
@AllArgsConstructor
public class EmailValidatorStrategy implements UserValidationStrategy {

    private final UserRepository repository;

    @Override
    public boolean isValid(User user) {
        if (user.getEmail() != null) {
            User userFoundByEmail = repository.findByEmail(user.getEmail());

            if (userFoundByEmail != null && !userFoundByEmail.getId().equals(user.getId())) {
                return false;
            }
        }
        return true;
    }
}
