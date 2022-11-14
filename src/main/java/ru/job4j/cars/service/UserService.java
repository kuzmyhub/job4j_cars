package ru.job4j.cars.service;

import ru.job4j.cars.model.User;

import java.util.Optional;

public interface UserService {

     User add(User user);

     Optional<User> findById(int id);

     Optional<User> findByLoginAndPassword(User user);

     void update(User user);

     Optional<User> findParticipatesByUser(int id);
}
