package ru.job4j.cars.repository;

import ru.job4j.cars.model.Driver;
import ru.job4j.cars.model.User;

import java.util.Optional;

public interface DriverRepository {

    Driver add(Driver driver);

    Optional<Driver> findByUser(User user);
}
