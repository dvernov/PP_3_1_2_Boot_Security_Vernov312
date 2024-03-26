package ru.kata.spring.boot_security.demo.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Component
public class Init implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public Init(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        if (userRepository.findAll().isEmpty() && roleRepository.findAll().isEmpty() ) {
            Role roleUser = new Role("USER");
            Role roleAdmin = new Role("ADMIN");

            User user = new User("Vasya", "Ivanov");
            user.setUsername("user");
            user.setPassword("user");

            User user1 = new User("Petya", "Valenok");
            user1.setUsername("user1");
            user1.setPassword("user1");

            roleUser.addUserToRole(user);
            user.addRoleToUser(roleAdmin);
            user.addRoleToUser(roleUser);
            user1.addRoleToUser(roleUser);

            roleRepository.saveAll(List.of(roleUser, roleAdmin));
            userRepository.saveAll(List.of(user1, user));
        }

    }
}
