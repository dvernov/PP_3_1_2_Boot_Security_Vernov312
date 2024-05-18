package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestUserController {

    private final UserService userService;
    private final RoleService roleService;


    public RestUserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> apiGetAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);

    }

    @GetMapping("/users/roles")
    public ResponseEntity<List<Role>> apiGetAllRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }

    @GetMapping("/users/roles/{id}")
    public ResponseEntity<Role> apiGetRoleById(@PathVariable Long id) {
        return new ResponseEntity<>(roleService.getRole(id), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> apiGetUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> apiDeleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("User was successfully deleted", HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<String> apiAddUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>("User was successfully added", HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<String> apiUpdateUser(@RequestBody User user) {
        userService.updateUser(user);
        return new ResponseEntity<>("User was successfully updated", HttpStatus.OK);
    }
}
