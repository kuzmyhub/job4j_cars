package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repository.EngineRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class SimpleEngineService implements EngineService {

    private EngineRepository hibernateEngineRepository;

    public List<Engine> findAll() {
        return hibernateEngineRepository.findAll();
    }

    public Optional<Engine> findById(int id) {
        return hibernateEngineRepository.findById(id);
    }
}
