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
import ru.job4j.cars.util.SessionUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@ThreadSafe
@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/formRegistration")
    public String formRegistrationUser(Model model, HttpSession httpSession) {
        User user = SessionUser.getSession(httpSession);
        model.addAttribute("registrationUser", new User());
        model.addAttribute("user", user);
        return "user/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User registrationUser) {
        userService.add(registrationUser);
        return "redirect:/carShop";
    }

    @GetMapping("/formLogin")
    public String formLogin(Model model, HttpSession httpSession) {
        User user = SessionUser.getSession(httpSession);
        model.addAttribute("loginUser", new User());
        model.addAttribute("user", user);
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest httpServletRequest) {
        Optional<User> optionalUser = userService.findByLoginAndPassword(user);
        if (optionalUser.isEmpty()) {
            return "redirect:/formLogin";
        }
        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.setAttribute("user", optionalUser.get());
        return "redirect:/carShop";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/carShop";
    }
}
