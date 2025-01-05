package io.github.api.repositories;

import io.github.api.domain.UserModel;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserModelRepository extends JpaRepository<UserModel, String> {
    Optional<UserModel> findByCpf(@CPF String cpf);
}
