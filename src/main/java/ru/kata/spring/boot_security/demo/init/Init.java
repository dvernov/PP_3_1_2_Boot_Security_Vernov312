package ru.kata.spring.boot_security.demo.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;
import java.util.Set;

@Component
public class Init implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public Init(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if (roleRepository.findAll().isEmpty()) {
            Role roleUser = new Role("USER");
            Role roleAdmin = new Role("ADMIN");
            roleRepository.saveAll(List.of(roleUser, roleAdmin));
        }


        if (userRepository.findAll().isEmpty()) {
            User user = new User("Vasya", "Ivanov", "admin", "admin@mail.ru", 44);
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRoles(Set.of(roleRepository.findByName("ADMIN"), roleRepository.findByName("USER")));

            User user1 = new User("Petya", "Valenok", "user", "user@mail.ru", 55);
            user1.setPassword(passwordEncoder.encode("user"));
            user1.setRoles(Set.of(roleRepository.findByName("USER")));

            User user2 = new User("test", "test", "test", "test@mail.ru", 22);
            user2.setPassword(passwordEncoder.encode("test"));
            user2.setRoles(Set.of(roleRepository.findByName("ADMIN")));

            userRepository.saveAll(List.of(user1, user, user2));
        }
    }
}
