package io.github.api.service;

import io.github.api.domain.User;
import io.github.api.repositories.UserModelRepository;
import io.github.api.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserModelRepository repository;
    private final UserValidator validator;
    private final PasswordEncoder encoder;

    public User createUser(User user){
        validator.validate(user);
        user.setRoles(List.of("USER"));
        var password = user.getPassword();
        user.setPassword(encoder.encode(password));
        return repository.save(user);
    }

    public User createAdminUser(User user) {
        validator.validate(user);
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
        return repository.findById(id);
    }
}
