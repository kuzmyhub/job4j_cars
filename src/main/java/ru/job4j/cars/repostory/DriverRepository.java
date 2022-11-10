package ru.job4j.cars.repostory;

import ru.job4j.cars.model.Driver;
import ru.job4j.cars.model.User;

import java.util.Map;
import java.util.Optional;

public interface DriverRepository {

    Driver add(Driver driver);

    Optional<Driver> findByUser(User user);
}
