package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.servlet.http.HttpServletRequest;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/register")
    public String addingUser(@ModelAttribute("userForm") User userForm
            , @RequestParam(value = "role", required = false) Long roleId) {

        if (userForm.getId() == null) {
            userForm.addRoleToUser(roleService.getRole(roleId));
            userService.saveUser(userForm);
        } else {
            userService.updateUser(userForm);
        }
        return "redirect:/admin";
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
        model.addAttribute("roles", roleService.getAllRoles());
        return "add-user";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") Long id, HttpServletRequest request) {
        userService.deleteUser(id);
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("userList", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/user")
    public String userPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUserName(userDetails.getUsername());
        model.addAttribute("userForm", user);
        return "user";
    }


}
