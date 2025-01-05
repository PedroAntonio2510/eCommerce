package io.github.api.service;

import io.github.api.domain.UserModel;
import io.github.api.repositories.UserModelRepository;
import io.github.api.validator.UserModelValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserModelService {

    private final UserModelRepository repository;
    private final UserModelValidator validator;


    public UserModel createUser(UserModel userModel){
        validator.validate(userModel);
        userModel.setRoles(List.of("USER"));
        //var password = userModel.getPassword();
        //userModel.setPassword(encoder.encode(password));
        return repository.save(userModel);
    }

    public UserModel createAdminUser(UserModel userModel) {
        validator.validate(userModel);
        return repository.save(userModel);
    }

    public void deleteUser(String cpf) {
        repository.deleteByCpf(cpf);
    }
}
