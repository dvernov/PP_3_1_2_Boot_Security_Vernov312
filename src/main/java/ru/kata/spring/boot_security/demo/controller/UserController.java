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
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/register")
    public String addingUser(@ModelAttribute("newUserForm") User newUserForm
            , @RequestParam(value = "role") List<Long> listOfRoleID) {

        for (Long roleId : listOfRoleID) {
            newUserForm.setRoles(Set.of(roleService.getRole(roleId)));
        }

        if (userService.findByUserName(newUserForm.getUsername()) == null) {
            userService.saveUser(newUserForm);
        } else {
            userService.updateUser(newUserForm);
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
    public String updateUser(@ModelAttribute User user
            , @RequestParam(value = "role") List<Long> listOfRoleID) {

        for (Long roleId : listOfRoleID) {
            user.setRoles(Set.of(roleService.getRole(roleId)));
//            user.addRoleToUser(roleService.getRole(roleId));
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") Long id, HttpServletRequest request) {
        userService.deleteUser(id);
        return "redirect:" + request.getHeader("Referer");
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
