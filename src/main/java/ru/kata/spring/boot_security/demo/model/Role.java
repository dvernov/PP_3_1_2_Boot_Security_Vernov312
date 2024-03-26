package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Transient
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }


    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUserToRole(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
    }
    @Override
    public String getAuthority() {
        return getName();
    }
}
