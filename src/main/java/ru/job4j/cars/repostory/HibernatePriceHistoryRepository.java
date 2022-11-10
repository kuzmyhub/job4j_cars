package ru.job4j.cars.repostory;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.PriceHistory;

@ThreadSafe
@Repository
@AllArgsConstructor
public class HibernatePriceHistoryRepository implements PriceHistoryRepository {

    private CrudRepository crudRepository;

    public PriceHistory add(PriceHistory priceHistory) {
        crudRepository.run(session -> session.save(priceHistory));
        return priceHistory;
    }
}
