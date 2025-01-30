package project.springpp.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.springpp.model.User;
import project.springpp.service.RoleService;
import project.springpp.service.UserService;

import java.util.Optional;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    // Показ всех пользователей
    @GetMapping
    public String allUsers(ModelMap model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", new User());  // Добавим пустого пользователя для формы добавления
        model.addAttribute("allRoles", roleService.findAll());  // Передаем все роли
        return "admin_panel";
    }

    // Показ формы для добавления нового пользователя
    @GetMapping("/add")
    public String addUserForm(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.findAll());
        return "admin_panel";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "admin_panel";
        }
        Optional<User> userWithSameEmail = userService.findByEmail(user.getEmail());
        if (userWithSameEmail.isPresent()) {
            result.rejectValue("email", "error.user", "Этот email уже используется другим пользователем.");
            return "admin_panel";
        }
        userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String updateUser(@PathVariable("id") Long id, ModelMap model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.findAll());
        return "admin_panel";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, @ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "admin_panel";
        }
        Optional<User> userWithSameEmail = userService.findByEmail(user.getEmail());
        if (userWithSameEmail.isPresent() && !userWithSameEmail.get().getId().equals(id)) {
            result.rejectValue("email", "error.user", "Этот email уже используется другим пользователем.");
            return "admin_panel";
        }
        userService.update(id, user);
        return "redirect:/admin";
    }


    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}