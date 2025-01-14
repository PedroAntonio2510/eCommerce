package io.github.api.security;

import io.github.api.domain.User;
import io.github.api.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginSocialSucessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;
    private static final String DefaultPassword = "ecommerceAPI";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException,
            ServletException {

        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = auth2AuthenticationToken.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        System.out.println("Email: " + email);

        User user = userService.getByEmail(email);
        System.out.println("User from database: " + user);

        if (user == null) {
            user = registerUser(email);
        }

        CustomAuthentication customAuthentication = new CustomAuthentication(user);

        SecurityContextHolder.getContext().setAuthentication(customAuthentication);

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private User registerUser(String email) {
        User user;
        user = new User();

        user.setEmail(email);
        user.setLogin(getLoginByEmail(email));
        user.setPassword(DefaultPassword);

        userService.createOAuthUser(user);
        return user;
    }

    private String getLoginByEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }

}
