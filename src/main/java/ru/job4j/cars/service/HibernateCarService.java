package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.HibernateCarRepository;

import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class HibernateCarService implements CarService {

    private HibernateCarRepository store;

    public Optional<Car> add(Car car) {
        return store.add(car);
    }

    public Optional<Car> findById(int id) {
        return store.findById(id);
    }

    public Car update(Car car) {
        return store.update(car);
    }
}
