package io.github.api.validator;

import io.github.api.domain.UserModel;
import io.github.api.domain.exceptions.ObjectDuplicateException;
import io.github.api.repositories.UserModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserModelValidator {

    private final UserModelRepository repository;

    public void validate(UserModel userModel) {
        if (existsUser(userModel)) {
            throw new ObjectDuplicateException("User already registered");
        }
    }

    private boolean existsUser(UserModel userModel) {
        Optional<UserModel> userFound = repository
                .findByCpf(userModel.getCpf());
        if  (userModel.getId() == null) {
            return userFound.isPresent();
        }
        return userFound
                .map(UserModel::getId)
                .stream()
                .anyMatch(id -> !id.equals(userModel.getId()));
    }
}
