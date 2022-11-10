package ru.job4j.cars.servise;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Driver;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repostory.HibernateDriverRepository;

import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class HibernateDriverService implements DriverService {

    private HibernateDriverRepository store;

    public Driver add(Driver driver) {
        return store.add(driver);
    }

    public Optional<Driver> findByUser(User user) {
        return store.findByUser(user);
    }
}
