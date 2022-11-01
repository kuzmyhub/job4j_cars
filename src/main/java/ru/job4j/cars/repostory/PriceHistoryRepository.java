package ru.job4j.cars.repostory;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;

@ThreadSafe
@Repository
@AllArgsConstructor
public class PriceHistoryRepository {

    private CrudRepository crudRepository;

}
