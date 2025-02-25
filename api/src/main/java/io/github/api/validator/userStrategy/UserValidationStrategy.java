package io.github.api.validator.userStrategy;

import io.github.api.domain.User;

public interface UserValidationStrategy {
    boolean isValid(User user);

}
