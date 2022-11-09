package ru.job4j.cars.repostory;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Driver;
import ru.job4j.cars.model.User;

import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class DriverRepository {

    private CrudRepository crudRepository;

    private static final String SELECT = "SELECT DISTINCT d FROM Driver d"
            + " JOIN FETCH d.user";

    private static final String BY_USER = "WHERE d.user = :fUser";

    public Driver add(Driver driver) {
        crudRepository.run(session -> session.save(driver));
        return driver;
    }

    public Optional<Driver> findByUser(User user) {
        return crudRepository.optional(
                String.format("%s %s", SELECT, BY_USER),
                Driver.class,
                Map.of("fUser", user)
        );
    }

}
