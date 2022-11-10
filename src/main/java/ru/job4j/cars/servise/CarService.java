package ru.job4j.cars.servise;

import ru.job4j.cars.model.Car;

import java.util.Optional;

public interface CarService {

     Car add(Car car);

     Optional<Car> findById(int id);

     Car update(Car car);
}
