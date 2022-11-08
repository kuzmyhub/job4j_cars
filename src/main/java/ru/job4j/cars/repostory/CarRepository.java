package ru.job4j.cars.repostory;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class CarRepository {

    private static final String SELECT = "SELECT DISTINCT c FROM Car c"
            + " JOIN FETCH c.owners";

    private CrudRepository crudRepository;

    public Car add(Car car) {
        crudRepository.run(session -> session.save(car));
        return car;
    }

    public Optional<Car> findById(int id) {
        return crudRepository.optional(SELECT,
                Car.class,
                Map.of("fId", id)
        );
    }
}
