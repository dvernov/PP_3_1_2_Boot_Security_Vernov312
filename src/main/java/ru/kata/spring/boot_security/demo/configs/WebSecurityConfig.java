package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final FailureUserHandler failureUserHandler;
    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(SuccessUserHandler successUserHandler, FailureUserHandler failureUserHandler, UserDetailsService userDetailsService) {
        this.successUserHandler = successUserHandler;
        this.failureUserHandler = failureUserHandler;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/user").access("hasRole('USER') or hasRole('ADMIN')")
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/api/**").permitAll()
                .and()
                .csrf().disable()
                .formLogin()
                .loginPage("/home")
                .loginProcessingUrl("/login")
                .permitAll()
                .successHandler(successUserHandler)
                .failureHandler(failureUserHandler)
                .and()
                .logout()
                .logoutSuccessUrl("/home")
                .permitAll();
    }
}