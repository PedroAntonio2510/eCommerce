package io.github.api.validator;

import io.github.api.domain.User;
import io.github.api.domain.exceptions.ObjectDuplicateException;
import io.github.api.validator.codeVerificationStrategy.EmailVerificationCodeStrategy;
import io.github.api.validator.userStrategy.UserValidationStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
public class UserValidator {

    private final Map<String, UserValidationStrategy> mapUserStrategy;
    private final Map<String, EmailVerificationCodeStrategy> mapVerificationCodeStrategy;

    public boolean isUserValid(User user, String strategy) {
        boolean strategyImpl = mapUserStrategy.get(strategy).isValid(user);
        if (strategyImpl) {
            return strategyImpl;
        }
        throw new ObjectDuplicateException("Usuario ja existe");
    }

    public boolean validateVerificationCode(String verificationCode) {
        boolean emailCodeValid = mapVerificationCodeStrategy.get("emailcode_verification").isEmailCodeValid(verificationCode);

        return emailCodeValid;
    }

}
