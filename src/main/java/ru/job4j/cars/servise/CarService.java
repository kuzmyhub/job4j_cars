package ru.job4j.cars.servise;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repostory.CarRepository;

import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class CarService {

    private CarRepository store;

    public Car add(Car car) {
        return store.add(car);
    }

    public Optional<Car> findById(int id) {
        return store.findById(id);
    }

    public Car update(Car car) {
        return store.update(car);
    }
}
