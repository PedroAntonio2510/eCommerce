package io.github.api.controller;

import io.github.api.security.CustomAuthentication;
import io.github.api.security.JwtService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class LoginController {

    @GetMapping
    @ResponseBody
    public String homePage(Authentication authentication) {
        if (authentication instanceof CustomAuthentication customAuth) {
            System.out.println(customAuth.getUser());
        }
        return "Olá " + authentication.getName();
    }

}
