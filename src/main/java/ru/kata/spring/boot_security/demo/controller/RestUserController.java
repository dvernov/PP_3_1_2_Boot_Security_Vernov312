package ru.kata.spring.boot_security.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.DTO.UserCreateDto;
import ru.kata.spring.boot_security.demo.DTO.UserDto;
import ru.kata.spring.boot_security.demo.mappers.UserMapper;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Validated
@Tag(name = "User API", description = "API to work with users")
public class RestUserController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserMapper userMapper;

    public RestUserController(UserService userService, RoleService roleService, UserMapper userMapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.userMapper = userMapper;
    }

    @GetMapping("/users")
    @Operation(summary = "Get all users")
    public ResponseEntity<List<UserDto>> apiGetAllUsers() {
        List<UserDto> users = userService.getAllUsers()
                .stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/roles")
    @Operation(summary = "Get all roles")
    public ResponseEntity<List<Role>> apiGetAllRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }

    @GetMapping("/users/roles/{id}")
    @Operation(summary = "Get role by id")
    public ResponseEntity<Role> apiGetRoleById(@PathVariable Long id) {
        return new ResponseEntity<>(roleService.getRole(id), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    @Operation(summary = "Get user by id")
    public ResponseEntity<UserDto> apiGetUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        UserDto userDto = userMapper.userToUserDto(user);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/users/{id}")
    @Operation(summary = "Delete user by id")
    public ResponseEntity<String> apiDeleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("User was successfully deleted", HttpStatus.OK);
    }


    @PostMapping("/users")
    @Operation(summary = "Add user")
    public ResponseEntity<String> apiAddUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        if (userService.findByUserName(userCreateDto.getUsername()) != null) {
            return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
        }
        User user = userMapper.userCreateDtoToUser(userCreateDto);
        if (userService.saveUser(user)) {
            return new ResponseEntity<>("User was successfully added", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User was not added", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/")
    @Operation(summary = "Update user")
    public ResponseEntity<String> apiUpdateUser(@RequestBody User user) {
        userService.updateUser(user);
        return new ResponseEntity<>("User was successfully updated", HttpStatus.OK);
    }
}
