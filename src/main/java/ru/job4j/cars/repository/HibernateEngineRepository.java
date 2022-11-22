package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class HibernateEngineRepository implements EngineRepository {

    private TemplateRepository hibernateTemplateRepository;

    private static final String SELECT = "FROM Engine e";

    private static final String BY_ID = "WHERE e.id = :fId";

    public Optional<Engine> add(Engine engine) {
        Optional<Engine> addingEngine;
        try {
            hibernateTemplateRepository.run(session -> session.save(engine));
            addingEngine = Optional.of(engine);
        } catch (Exception e) {
            addingEngine = Optional.empty();
        }
        return addingEngine;
    }

    public List<Engine> findAll() {
        return hibernateTemplateRepository.query(
                SELECT,
                Engine.class
        );
    }

    public Optional<Engine> findById(int id) {
        return hibernateTemplateRepository.optional(
                String.format("%s %s", SELECT, BY_ID),
                Engine.class,
                Map.of("fId", id));
    }
}
