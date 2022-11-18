package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.UserRepository;

import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class SimpleUserService implements UserService {

    private final UserRepository hibernateUserRepository;

    public Optional<User> add(User user) {
        return hibernateUserRepository.add(user);
    }

    public Optional<User> findById(int id) {
        return hibernateUserRepository.findById(id);
    }

    public Optional<User> findByLoginAndPassword(User user) {
        return hibernateUserRepository.findByLoginAndPassword(user);
    }

    public void update(User user) {
        hibernateUserRepository.update(user);
    }

    public Optional<User> findParticipatesByUser(int id) {
        return hibernateUserRepository.findParticipatesByUser(id);
    }
}
