package ru.job4j.cars.repostory;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

@ThreadSafe
@Repository
@AllArgsConstructor
public class CarRepository {

    private CrudRepository crudRepository;

    public Car add(Car car) {
        crudRepository.run(session -> session.save(car));
        return car;
    }
}
