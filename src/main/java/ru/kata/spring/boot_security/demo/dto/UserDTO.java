package ru.kata.spring.boot_security.demo.dto;

import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import java.util.Set;

public class UserDTO {
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;
    private int age;

    @ManyToMany
    private Set<Role> roles;
}
//TODO?
