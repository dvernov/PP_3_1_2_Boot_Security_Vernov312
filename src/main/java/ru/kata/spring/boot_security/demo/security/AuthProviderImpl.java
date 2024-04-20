package ru.kata.spring.boot_security.demo.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class AuthProviderImpl implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    public AuthProviderImpl(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;

    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        if (!password.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect password!");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
