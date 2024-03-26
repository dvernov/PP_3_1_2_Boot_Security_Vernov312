package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

//    @PostConstruct
//    public void init() {
//        Role roleUser = new Role("ROLE_USER");
//        Role roleAdmin = new Role("ROLE_ADMIN");
//        roleRepository.saveAll(List.of(roleUser, roleAdmin));
//    }

    @Override
    public Role getRole(Long id) {
        return roleRepository.getById(id);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
