package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/")
    public String showAllUsers(Model model) {
        model.addAttribute("userList", userService.getAllUsers());

        return "index";
    }

    @PostMapping("/register")
    public String addingUser(@ModelAttribute("userForm") User userForm
            , @RequestParam(value = "role", required = false) Long roleId
            , Model model) {

        if (userForm.getId() == null) {
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            userForm.addRoleToUser(roleService.getRole(roleId));
            userService.saveUser(userForm);
        } else {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            userService.updateUser(userForm);
        }
        return "redirect:/";
    }

    @GetMapping("/addUser")
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("userForm", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "add-user";
    }

    @PostMapping("/updateUser")
    public String updateUser(@RequestParam(value = "id") Long id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("userForm", user);
        model.addAttribute("roles", user.getRoles());
        return "add-user";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/";
    }


}
