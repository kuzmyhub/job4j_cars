package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Driver;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.DriverRepository;

import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class SimpleDriverService implements DriverService {

    private DriverRepository hibernateDriverRepository;

    public Optional<Driver> add(Driver driver) {
        return hibernateDriverRepository.add(driver);
    }

    public Optional<Driver> findByUser(User user) {
        return hibernateDriverRepository.findByUser(user);
    }
}
