package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.PostService;
import ru.job4j.cars.service.UserService;
import ru.job4j.cars.util.SessionUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ThreadSafe
@Controller
@AllArgsConstructor
public class UserController {

    private final UserService hibernateUserService;
    private final PostService simplePostService;

    @GetMapping("/formRegistration")
    public String formRegistrationUser(Model model, HttpSession httpSession) {
        User user = SessionUser.getSession(httpSession);
        model.addAttribute("registrationUser", new User());
        model.addAttribute("user", user);
        return "user/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User registrationUser) {
        hibernateUserService.add(registrationUser);
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
        Optional<User> optionalUser = hibernateUserService.findByLoginAndPassword(user);
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

    @GetMapping("/subscribe")
    public String subscribe(@ModelAttribute(name = "postId") int postId,
                            HttpSession httpSession) {
        User user = SessionUser.getSession(httpSession);
        Optional<Post> optionalPost = simplePostService.findById(postId);
        if (optionalPost.isEmpty()) {
            return "404";
        }
        Post post = optionalPost.get();
        Optional<User> optionalUserWithParticipates
                = hibernateUserService.findParticipatesByUser(user.getId());
        if (optionalUserWithParticipates.isEmpty()) {
            user.setParticipates(new ArrayList<>());
            optionalUserWithParticipates = Optional.of(user);
        }
        User userWithParticipates = optionalUserWithParticipates.get();
        List<Post> participates;
        if (userWithParticipates.getParticipates() == null) {
            participates = new ArrayList<>();
            userWithParticipates.setParticipates(participates);
        } else {
            participates = userWithParticipates.getParticipates();
        }
        participates.add(post);
        user.setParticipates(participates);
        hibernateUserService.update(user);
        return "redirect:/openPost/" + postId;
    }
}
