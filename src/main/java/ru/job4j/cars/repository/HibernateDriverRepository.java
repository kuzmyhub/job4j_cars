package ru.job4j.cars.repository;

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
public class HibernateDriverRepository implements DriverRepository {

    private TemplateRepository hibernateTemplateRepository;

    private static final String SELECT = "FROM Driver d"
            + " JOIN FETCH d.user";

    private static final String BY_USER = "WHERE d.user = :fUser";

    public Optional<Driver> add(Driver driver) {
        Optional<Driver> addingDriver;
        try {
            addingDriver = Optional.of(driver);
            hibernateTemplateRepository.run(session -> session.save(driver));
        } catch (Exception e) {
            addingDriver = Optional.empty();
        }
        return addingDriver;
    }

    public Optional<Driver> findByUser(User user) {
        return hibernateTemplateRepository.optional(
                String.format("%s %s", SELECT, BY_USER),
                Driver.class,
                Map.of("fUser", user)
        );
    }

}
