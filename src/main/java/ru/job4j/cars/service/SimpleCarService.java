package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.CarRepository;

import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class SimpleCarService implements CarService {

    private CarRepository hibernateCarRepository;

    public Optional<Car> add(Car car) {
        return hibernateCarRepository.add(car);
    }

    public Optional<Car> findById(int id) {
        return hibernateCarRepository.findById(id);
    }

    public Car update(Car car) {
        return hibernateCarRepository.update(car);
    }
}
