package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kata.spring.boot_security.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u join fetch u.roles where u.username = :username")
    User findByUsername(String username);

    @Query("select u from User u join fetch u.roles where u.email = :email")
    User findUserByEmail(String email);
}
