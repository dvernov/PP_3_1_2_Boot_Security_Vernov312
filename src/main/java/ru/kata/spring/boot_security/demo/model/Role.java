package ru.kata.spring.boot_security.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @Transient
    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new TreeSet<>();

    @Override
    public String toString() {
        return name;
    }

    public void addUserToRole(User user) {
        users.add(user);
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
