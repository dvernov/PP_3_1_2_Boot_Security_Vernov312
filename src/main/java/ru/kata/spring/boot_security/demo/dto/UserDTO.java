//package ru.kata.spring.boot_security.demo.dto;
//
//import ru.kata.spring.boot_security.demo.model.Role;
//
//import javax.persistence.Column;
//import javax.persistence.ManyToMany;
//import java.util.Set;
//
//public class UserDTO {
//    private String firstName;
//    private String lastName;
//
//    @Column(unique = true)
//    private String username;
//
//    private String password;
//
//    private String email;
//    private int age;
//
//    @ManyToMany
//    private Set<Role> roles;
//
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public Set<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Set<Role> roles) {
//        this.roles = roles;
//    }
//}
////TODO?
