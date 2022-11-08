package ru.job4j.cars.repostory;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.PriceHistory;

import java.util.List;
import java.util.Map;

@ThreadSafe
@Repository
@AllArgsConstructor
public class PriceHistoryRepository {

    private CrudRepository crudRepository;

    public PriceHistory add(PriceHistory priceHistory) {
        crudRepository.run(session -> session.save(priceHistory));
        return priceHistory;
    }
}
