package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repository.HibernateEngineRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class HibernateEngineService implements EngineService {

    private HibernateEngineRepository store;

    public List<Engine> findAll() {
        return store.findAll();
    }

    public Optional<Engine> findById(int id) {
        return store.findById(id);
    }
}
