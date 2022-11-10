package ru.job4j.cars.servise;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repostory.UserRepository;

import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository store;

    public User add(User user) {
        return store.add(user);
    }

    public Optional<User> findById(int id) {
        return store.findById(id);
    }

    public Optional<User> findByLoginAndPassword(User user) {
        return store.findByLoginAndPassword(user);
    }

    public void update(User user) {
        store.update(user);
    }

    public Optional<User> findParticipatesByUser(int id) {
        return store.findParticipatesByUser(id);
    }
}
