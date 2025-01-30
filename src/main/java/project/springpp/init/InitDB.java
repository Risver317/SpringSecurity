package project.springpp.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.springpp.model.Role;
import project.springpp.model.User;
import project.springpp.service.RoleService;
import project.springpp.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;


@Component
public class InitDB {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public InitDB(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        if (roleService.findAll().isEmpty()) {
            Role adminRole = new Role("ROLE_Admin");
            Role userRole = new Role("ROLE_User");

            roleService.add(adminRole);
            roleService.add(userRole);

            Set<Role> rolesAdmin = new HashSet<>();
            rolesAdmin.add(adminRole);

            Set<Role> rolesUser = new HashSet<>();
            rolesUser.add(userRole);

            User admin = new User("Misha", "Almazov", "misha@mail.ru", ("1234"), rolesAdmin);
            User user = new User("Jon", "Smith", "jon@gmail.com", ("1234"), rolesUser);

            userService.add(admin);
            userService.add(user);
        }
    }
}
