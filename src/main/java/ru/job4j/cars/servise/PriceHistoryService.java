package ru.job4j.cars.servise;

import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.repostory.HibernatePriceHistoryRepository;

public interface PriceHistoryService {

    PriceHistory add(PriceHistory priceHistory);
}
