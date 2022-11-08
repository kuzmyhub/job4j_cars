package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.cars.model.User;
import ru.job4j.cars.servise.UserService;

@ThreadSafe
@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/formRegistration")
    public String formRegistrationUser(Model model) {
        model.addAttribute("user", new User());
        return "user/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User user) {
        System.out.println(user);
        userService.add(user);
        return "redirect:/carShop";
    }
}
