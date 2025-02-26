package io.github.api.service;

import io.github.api.domain.User;
import io.github.api.repositories.UserRepository;
import io.github.api.util.RandomString;
import io.github.api.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${rabbitmq.order.notification.exchange}")
    private String notificationExchange;

    private final UserRepository repository;
    private final UserValidator validator;
    private final PasswordEncoder encoder;
    private final RabbitMqNotificationService rabbitMqNotificationService;

    public User registerUser(User user){
        validator.isUserValid(user, "cpf_validation");
        validator.isUserValid(user, "email_validation");

        user.setRoles(List.of("USER"));
        user.setPassword(encoder.encode(user.getPassword()));

        String randomCode = RandomString.generateRandomString(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);

        User newUser = repository.save(user);

        rabbitMqNotificationService.notify(user, notificationExchange, "email.verification");

        return newUser;
    }

    public User createOAuthUser(User user){
        validator.isUserValid(user, "cpf_validation");
        validator.isUserValid(user, "email_validation");

        user.setRoles(List.of("USER"));
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public User createAdminUser(User user) {
        validator.isUserValid(user, "cpf_validation");
        validator.isUserValid(user, "email_validation");
        user.setRoles(List.of("MANAGER"));
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public void deleteUser(String id) {
        repository.deleteById(id);
    }

    public User getByLogin(String login) {
        return repository.findByLogin(login);
    }

    public User getByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Optional<User> getById(String id) {
        if (id.isEmpty() || id == null) {
            throw new IllegalArgumentException("The id isn't valid");
        }
        return repository.findById(id);
    }
}
