package ru.job4j.cars.servise;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repostory.EngineRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class EngineService {

    private EngineRepository store;

    public List<Engine> findAll() {
        return store.findAll();
    }

    public Optional<Engine> findById(int id) {
        return store.findById(id);
    }
}
