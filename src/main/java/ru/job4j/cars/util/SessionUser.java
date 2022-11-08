package ru.job4j.cars.util;

import ru.job4j.cars.model.User;

import javax.servlet.http.HttpSession;

public final class SessionUser {

    public static User getSession(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setLogin("Гость");
        }
        return user;
    }
}
