package ru.job4j.cars.servise;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Driver;
import ru.job4j.cars.repostory.DriverRepository;

@ThreadSafe
@Service
@AllArgsConstructor
public class DriverService {

    private DriverRepository store;

    public Driver add(Driver driver) {
        return store.add(driver);
    }
}
