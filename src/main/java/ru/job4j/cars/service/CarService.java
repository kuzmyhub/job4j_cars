package ru.job4j.cars.service;

import ru.job4j.cars.model.Car;

import java.util.Optional;

public interface CarService {

     Optional<Car> add(Car car);

     Optional<Car> findById(int id);

     Car update(Car car);
}
