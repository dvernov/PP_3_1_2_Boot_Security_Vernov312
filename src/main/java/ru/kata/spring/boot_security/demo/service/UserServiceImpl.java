package ru.kata.spring.boot_security.demo.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.util.CurrentUserUtils;

import java.util.List;
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUserUtils currentUserUtils;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, CurrentUserUtils currentUserUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.currentUserUtils = currentUserUtils;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        return userRepository.getUser(id);
    }

    @Override
    @Transactional
    public boolean deleteUser(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            log.info("User with id: " + id + " was deleted by " + currentUserUtils.getCurrentUsername());
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info("User with username: " + user.getUsername() + " was saved by " + currentUserUtils.getCurrentUsername());
        return true;
    }

    @Override
    @Transactional
    public boolean updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info("User with username: " + user.getUsername() + " was updated by " + currentUserUtils.getCurrentUsername());
        return true;
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

}
