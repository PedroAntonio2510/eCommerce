package io.github.api.validator.userStrategy.strategy;

import io.github.api.domain.User;
import io.github.api.repositories.UserRepository;
import io.github.api.validator.userStrategy.UserValidationStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component("isenable_validation")
@AllArgsConstructor
public class EmailEnabledStrategy implements UserValidationStrategy {

    private final UserRepository repository;

    @Override
    public boolean isValid(User user) {
        boolean enabled = user.isEnabled();
        if (enabled) {
            return true;
        }
        return false;
    }
}
