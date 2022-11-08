package ru.job4j.cars.repostory;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Driver;

@ThreadSafe
@Repository
@AllArgsConstructor
public class DriverRepository {

    private CrudRepository crudRepository;

    public Driver add(Driver driver) {
        crudRepository.run(session -> session.save(driver));
        return driver;
    }

}
