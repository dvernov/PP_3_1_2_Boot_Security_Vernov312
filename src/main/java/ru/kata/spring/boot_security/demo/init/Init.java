package ru.kata.spring.boot_security.demo.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;

@Component
public class Init implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public Init(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {

        if (userRepository.findAll().isEmpty() && roleRepository.findAll().isEmpty()) {
            Role roleUser = new Role("USER");
            Role roleAdmin = new Role("ADMIN");

            User user = new User("Vasya", "Ivanov", "admin@mail.ru", 44);
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin"));

            User user1 = new User("Petya", "Valenok", "user@mail.ru", 55);
            user1.setUsername("user");
            user1.setPassword(passwordEncoder.encode("user"));

            roleUser.addUserToRole(user);
            user.addRoleToUser(roleAdmin);
            user.addRoleToUser(roleUser);
            user1.addRoleToUser(roleUser);

            roleRepository.saveAll(List.of(roleUser, roleAdmin));
            userRepository.saveAll(List.of(user1, user));
        }

    }
}
