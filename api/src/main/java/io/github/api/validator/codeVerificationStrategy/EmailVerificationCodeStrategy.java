package io.github.api.validator.codeVerificationStrategy;

public interface EmailVerificationCodeStrategy {
    boolean isEmailCodeValid(String verificationCode);

}
