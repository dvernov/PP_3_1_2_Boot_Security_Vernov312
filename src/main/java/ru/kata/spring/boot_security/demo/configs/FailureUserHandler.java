package ru.kata.spring.boot_security.demo.configs;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FailureUserHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = exception.getLocalizedMessage();
        String errorType = exception.getClass().getSimpleName();
        // Добавьте код для передачи errorMessage на вашу страницу авторизации
        response.sendRedirect("/home?error=" + errorMessage + "&errorType=" + errorType);
    }
}