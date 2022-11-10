package ru.job4j.cars.repostory;

import ru.job4j.cars.model.User;

import java.util.Map;
import java.util.Optional;

public interface UserRepository {

     User add(User user);

     Optional<User> findById(int id);

     Optional<User> findByLoginAndPassword(User user);

     void update(User user);

     Optional<User> findParticipatesByUser(int id);
}
