package ru.job4j.cars.repostory;

import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EngineRepository {

    List<Engine> findAll();

    Optional<Engine> findById(int id);
}
