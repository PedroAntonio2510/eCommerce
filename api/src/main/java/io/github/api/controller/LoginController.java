package io.github.api.controller;

import io.github.api.security.CustomAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class LoginController {
    
    @GetMapping
    @ResponseBody
    public String homePage(Authentication authentication) {
        if (authentication instanceof CustomAuthentication customAuth) {
            System.out.println(customAuth.getUser());
        } else {
            System.out.println("Nenhum usuario encontrado");
        }
        return "Olá " + authentication.getName();
    }
}
