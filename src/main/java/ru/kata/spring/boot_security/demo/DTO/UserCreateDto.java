package ru.kata.spring.boot_security.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kata.spring.boot_security.demo.common.UniqueUsername;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {
    private String firstName;
    private String lastName;
    @NotBlank(message = "Username cannot be empty")
    @UniqueUsername
    private String username;
    @NotBlank(message = "Password cannot be empty")
    private String password;
    private String email;
    private int age;
    private Set<Role> roles;
}