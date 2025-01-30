package project.springpp.service;

import project.springpp.model.User;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


public interface UserService {
    void add(User user);

    void update(Long id, User user);

    void delete(Long id);

    List<User> findAll();

    User findById(Long id);

    Optional<User> findByEmail(String email);

    User oneUser(Principal principal);
}