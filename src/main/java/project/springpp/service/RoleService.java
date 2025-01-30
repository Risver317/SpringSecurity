package project.springpp.service;


import project.springpp.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> findAll();

    void add(Role role);

    Optional<Role> findByName(String name);


}