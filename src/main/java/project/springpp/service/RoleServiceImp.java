package project.springpp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.springpp.model.Role;
import project.springpp.repository.RoleRepository;


import java.util.List;
import java.util.Optional;


@Service
public class RoleServiceImp implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Transactional
    @Override
    public void add(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }
}