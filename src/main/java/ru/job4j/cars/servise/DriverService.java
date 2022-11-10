package ru.job4j.cars.servise;

import ru.job4j.cars.model.Driver;
import ru.job4j.cars.model.User;

import java.util.Optional;

public interface DriverService {

    public Driver add(Driver driver);

    public Optional<Driver> findByUser(User user);
}
