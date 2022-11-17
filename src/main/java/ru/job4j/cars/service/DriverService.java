package ru.job4j.cars.service;

import ru.job4j.cars.model.Driver;
import ru.job4j.cars.model.User;

import java.util.Optional;

public interface DriverService {

    Optional<Driver> add(Driver driver);

    Optional<Driver> findByUser(User user);
}
