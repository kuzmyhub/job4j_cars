package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.repository.PriceHistoryRepository;

import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class SimplePriceHistoryService implements PriceHistoryService {

    private PriceHistoryRepository hibernatePriceHistoryRepository;

    public Optional<PriceHistory> add(PriceHistory priceHistory) {
        return hibernatePriceHistoryRepository.add(priceHistory);
    }
}
