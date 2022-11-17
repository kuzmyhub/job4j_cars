package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.PriceHistory;

import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class HibernatePriceHistoryRepository implements PriceHistoryRepository {

    private TemplateRepository hibernateTemplateRepository;

    public Optional<PriceHistory> add(PriceHistory priceHistory) {
        Optional<PriceHistory> addingPriceHistory;
         try {
            hibernateTemplateRepository.run(session -> session.save(priceHistory));
            addingPriceHistory = Optional.of(priceHistory);
        } catch (Exception e) {
             addingPriceHistory = Optional.empty();
         }
        return addingPriceHistory;
    }
}
