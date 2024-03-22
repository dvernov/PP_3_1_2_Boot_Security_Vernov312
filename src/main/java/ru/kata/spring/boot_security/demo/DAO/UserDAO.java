package ru.kata.spring.boot_security.demo.DAO;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();
    void deleteUser(Long id);
    void saveUser(User user);
    User getUser(Long id);

}
