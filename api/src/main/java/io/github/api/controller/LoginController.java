package io.github.api.controller;

import io.github.api.security.CustomAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping
public class LoginController {

//    @GetMapping("/login")
//    public String paginaLogin() {
//        return "login";
//    }

    @GetMapping("/")
    @ResponseBody
    public String homePage(Authentication authentication) {
        if (authentication instanceof CustomAuthentication customAuth) {
            System.out.println(customAuth.getUser());
        }
        return "Olá " + authentication.getName();
    }

}
