package io.github.api.validator.codeVerificationStrategy.strategy;

import io.github.api.domain.User;
import io.github.api.repositories.UserRepository;
import io.github.api.validator.codeVerificationStrategy.EmailVerificationCodeStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component("emailcode_verification")
@AllArgsConstructor
public class VerificationCodeStrategy implements EmailVerificationCodeStrategy {

    private final UserRepository repository;


    @Override
    public boolean isEmailCodeValid(String verificationCode) {
        User userFound = repository.findByVerificationCode(verificationCode);
        if (userFound == null || userFound.isEnabled()) {
            return false;
        } else {
            userFound.setVerificationCode(null);
            userFound.setEnabled(true);
            repository.save(userFound);

            return true;
        }
    }
}
