package ru.job4j.cars.repository;

import ru.job4j.cars.model.Car;

import java.util.Map;
import java.util.Optional;

public interface CarRepository {

    Optional<Car> add(Car car);

    Optional<Car> findById(int id);

    Car update(Car car);
}
