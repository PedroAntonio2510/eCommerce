package io.github.api.repositories;

import io.github.api.domain.User;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByCpf(@CPF String cpf);

    User findByLogin(String login);

    User findByEmail(@Email String email);

}
