package ru.job4j.cars.repostory;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@ThreadSafe
@Repository
@AllArgsConstructor
public class PostRepository {

    private CrudRepository crudRepository;

    private static final String BY_LAST_DAY = "FROM Post p"
            + " JOIN FETCH p.user JOIN FETCH p.priceHistory JOIN FETCH p.car WHERE p.created > :fCreated";

    private static final String BY_AVAILABILITY_PHOTO = "FROM Post p"
            + " JOIN FETCH p.user JOIN FETCH p.priceHistory JOIN FETCH p.car WHERE p.photo IS NOT NULL";

    private static final String BY_BRAND = "FROM Post p"
            + " JOIN FETCH p.user JOIN FETCH p.priceHistory JOIN FETCH p.car WHERE p.brand = :fBrand";

    public List<Post> findByLastDay() {
        return crudRepository.query(
                BY_LAST_DAY,
                Post.class,
                Map.of(":fCreated", LocalDateTime.now().minusDays(1))
        );
    }

    public List<Post> findByAvailabilityPhoto() {
        return crudRepository.query(
                BY_AVAILABILITY_PHOTO,
                Post.class
                );
    }

    public List<Post> findByBrand(String brand) {
        return crudRepository.query(
                BY_BRAND,
                Post.class,
                Map.of("fBrand", brand)
        );
    }
}
