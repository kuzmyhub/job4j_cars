package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.repository.HibernatePriceHistoryRepository;

@ThreadSafe
@Service
@AllArgsConstructor
public class HibernatePriceHistoryService implements PriceHistoryService {

    private HibernatePriceHistoryRepository store;

    public PriceHistory add(PriceHistory priceHistory) {
        return store.add(priceHistory);
    }
}
