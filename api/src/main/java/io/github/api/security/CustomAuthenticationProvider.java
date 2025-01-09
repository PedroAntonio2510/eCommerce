package io.github.api.security;

import io.github.api.domain.User;
import io.github.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String incomePassword = authentication.getCredentials().toString();

        User userFound = userService.getByLogin(login);

        if (userFound == null) {
            throw new UsernameNotFoundException("login and/or password incorrect");
        }

        String encryptedPassword = userFound.getPassword();

        boolean matchesPassword = encoder.matches(incomePassword, encryptedPassword);

        if (matchesPassword) {
            return new CustomAuthentication(userFound);
        }
        throw new UsernameNotFoundException("Login or password are incorrect");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
