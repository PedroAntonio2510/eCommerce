package io.github.api.service;

import io.github.api.domain.UserModel;
import io.github.api.repositories.UserModelRepository;
import io.github.api.validator.UserModelValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserModelService {

    private final UserModelRepository repository;
    private final UserModelValidator validator;
    private final PasswordEncoder encoder;


    public UserModel createUser(UserModel userModel){
        validator.validate(userModel);
        userModel.setRoles(List.of("USER"));
        var password = userModel.getPassword();
        userModel.setPassword(encoder.encode(password));
        return repository.save(userModel);
    }

    public UserModel createAdminUser(UserModel userModel) {
        validator.validate(userModel);
        userModel.setPassword(encoder.encode(userModel.getPassword()));
        return repository.save(userModel);
    }

    public void deleteUser(String id) {
        repository.deleteById(id);
    }

    public Optional<UserModel> getByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }
}
