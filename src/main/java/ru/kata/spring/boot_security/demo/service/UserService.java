package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUser(Long id);

    boolean deleteUser(Long id);

    boolean saveUser(User user);

    boolean updateUser(User user);

    User findByUserName(String username);

    User findUserByEmail(String email);

}
