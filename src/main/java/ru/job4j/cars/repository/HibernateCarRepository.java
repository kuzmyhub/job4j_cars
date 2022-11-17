package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class HibernateCarRepository implements CarRepository {

    private static final String SELECT = "FROM Car c"
            + " JOIN FETCH c.owners";

    private static final String BY_ID = "WHERE c.id = :fId";

    private TemplateRepository hibernateTemplateRepository;

    public Optional<Car> add(Car car) {
        Optional<Car> addingCar;
        try {
            hibernateTemplateRepository.run(session -> session.save(car));
            addingCar = Optional.of(car);
        } catch (Exception e) {
            addingCar = Optional.empty();
        }
        return addingCar;
    }

    public Optional<Car> findById(int id) {
        return hibernateTemplateRepository.optional(
                String.format("%s %s", SELECT, BY_ID),
                Car.class,
                Map.of("fId", id)
        );
    }

    public Car update(Car car) {
        hibernateTemplateRepository.run(session -> session.update(car));
        return car;
    }
}
