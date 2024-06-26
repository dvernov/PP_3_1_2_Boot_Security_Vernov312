package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.security.AuthProviderImpl;

@Controller
public class LoginController {

    private final AuthProviderImpl authProvider;

    public LoginController(AuthProviderImpl authProvider) {
        this.authProvider = authProvider;
    }

    @PostMapping("/login")
    public String login(@RequestParam(value = "username") String username
            , @RequestParam(value = "password") String password
            , Model model) {

        try {
            SecurityContextHolder.getContext().setAuthentication(authProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)));
            return "redirect:/";
        } catch (BadCredentialsException e) {
            model.addAttribute("error", "Invalid username or password");
            return "redirect:/";
        }
    }

}
