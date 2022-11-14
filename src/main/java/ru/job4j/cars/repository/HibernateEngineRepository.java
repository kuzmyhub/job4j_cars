package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class HibernateEngineRepository implements EngineRepository {

    private CrudRepository crudRepository;

    private static final String SELECT = "FROM Engine e";

    private static final String BY_ID = "WHERE e.id = :fId";

    public List<Engine> findAll() {
        return crudRepository.query(
                SELECT,
                Engine.class
        );
    }

    public Optional<Engine> findById(int id) {
        return crudRepository.optional(
                String.format("%s %s", SELECT, BY_ID),
                Engine.class,
                Map.of("fId", id));
    }
}
