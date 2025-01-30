package project.springpp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.springpp.model.Role;
import project.springpp.model.User;
import project.springpp.repository.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImp implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Transactional
    @Override
    public void add(User user) {
        if (user.getRoles().isEmpty()) {
            Role defaultRole = roleService.findByName("ROLE_User").orElseThrow(() -> new RuntimeException("Role not found"));
            user.getRoles().add(defaultRole);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void update(Long id, User user) {
        User userToUpdate = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        userToUpdate.setRoles(user.getRoles());
        userRepository.save(userToUpdate);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User oneUser(Principal principal) {
        return userRepository.findByEmail(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

