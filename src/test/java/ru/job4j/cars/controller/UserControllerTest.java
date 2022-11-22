package ru.job4j.cars.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.PostService;
import ru.job4j.cars.service.SimplePostService;
import ru.job4j.cars.service.SimpleUserService;
import ru.job4j.cars.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Test
    public void whenFormRegistrationUser() {
        UserService simpleUserService = mock(SimpleUserService.class);
        PostService simplePostService = mock(SimplePostService.class);
        UserController userController = new UserController(
                simpleUserService, simplePostService
        );
        Model model = mock(Model.class);
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User();
        user.setLogin("Гость");
        User registrationUser = new User();
        String expected = "user/registration";
        String page = userController.formRegistrationUser(
                model, httpSession
        );
        verify(model).addAttribute("registrationUser", registrationUser);
        verify(model).addAttribute("user", user);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenRegistration() {
        UserService simpleUserService = mock(SimpleUserService.class);
        PostService simplePostService = mock(SimplePostService.class);
        UserController userController = new UserController(
                simpleUserService, simplePostService
        );
        User registrationUser = new User();
        String expected = "redirect:/carShop";
        String page = userController.registration(
                registrationUser
        );
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenFormLogin() {
        UserService simpleUserService = mock(SimpleUserService.class);
        PostService simplePostService = mock(SimplePostService.class);
        UserController userController = new UserController(
                simpleUserService, simplePostService
        );
        Model model = mock(Model.class);
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User();
        user.setLogin("Гость");
        User loginUser = new User();
        String expected = "user/login";
        String page = userController.formLogin(
                model, httpSession
        );
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("loginUser", loginUser);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenLoginThenSuccess() {
        UserService simpleUserService = mock(SimpleUserService.class);
        PostService simplePostService = mock(SimplePostService.class);
        UserController userController = new UserController(
                simpleUserService, simplePostService
        );
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User();
        user.setLogin("Гость");
        Optional<User> optionalUser = Optional.of(user);
        when(simpleUserService.findByLoginAndPassword(any())).thenReturn(optionalUser);
        when(httpServletRequest.getSession()).thenReturn(httpSession);
        String expected = "redirect:/carShop";
        String page = userController.login(user, httpServletRequest);
        verify(httpSession).setAttribute("user", optionalUser.get());
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenLoginThenNotSuccess() {
        UserService simpleUserService = mock(SimpleUserService.class);
        PostService simplePostService = mock(SimplePostService.class);
        UserController userController = new UserController(
                simpleUserService, simplePostService
        );
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        User user = new User();
        user.setLogin("Гость");
        Optional<User> optionalUser = Optional.empty();
        when(simpleUserService.findByLoginAndPassword(any())).thenReturn(optionalUser);
        String expected = "redirect:/formLogin";
        String page = userController.login(user, httpServletRequest);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenLogOut() {
        UserService simpleUserService = mock(SimpleUserService.class);
        PostService simplePostService = mock(SimplePostService.class);
        UserController userController = new UserController(
                simpleUserService, simplePostService
        );
        HttpSession httpSession = mock(HttpSession.class);
        String expected = "redirect:/carShop";
        String page = userController.logout(httpSession);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenSubscribeThenSuccess() {
        UserService simpleUserService = mock(SimpleUserService.class);
        PostService simplePostService = mock(SimplePostService.class);
        UserController userController = new UserController(
                simpleUserService, simplePostService
        );
        HttpSession httpSession = mock(HttpSession.class);
        int postId = 1;
        User user = new User();
        user.setLogin("Гость");
        Post post = new Post();
        post.setDescription("Продам авто");
        Optional<Post> optionalPost = Optional.of(post);
        Optional<User> optionalUser = Optional.of(user);
        when(simplePostService.findById(postId)).thenReturn(optionalPost);
        when(simpleUserService.findParticipatesByUser(any(Integer.class))).thenReturn(optionalUser);
        String expected = "redirect:/openPost/" + postId;
        String page = userController.subscribe(postId, httpSession);
        assertThat(page).isEqualTo(expected);
    }

    @Test
    public void whenSubscribeThenNotSuccess() {
        UserService simpleUserService = mock(SimpleUserService.class);
        PostService simplePostService = mock(SimplePostService.class);
        UserController userController = new UserController(
                simpleUserService, simplePostService
        );
        HttpSession httpSession = mock(HttpSession.class);
        int postId = 1;
        User user = new User();
        user.setLogin("Гость");
        Optional<Post> optionalPost = Optional.empty();
        when(simplePostService.findById(postId)).thenReturn(optionalPost);
        String expected = "404";
        String page = userController.subscribe(postId, httpSession);
        assertThat(page).isEqualTo(expected);
    }
}