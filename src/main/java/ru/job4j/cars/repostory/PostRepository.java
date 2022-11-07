package ru.job4j.cars.repostory;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PriceHistory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class PostRepository {

    private CrudRepository crudRepository;

    private static final String SELECT = "SELECT DISTINCT p FROM Post p"
            + " JOIN FETCH p.user"
            + " JOIN FETCH p.priceHistories"
            + " JOIN FETCH p.car";

    private static final String BY_ID = "WHERE p.id = :fId";

    private static final String BY_LAST_DAY =
            "WHERE p.created > :fCreated";

    private static final String BY_AVAILABILITY_PHOTO =
            "WHERE p.photo IS NOT NULL";

    private static final String BY_BRAND =
            "WHERE p.brand = :fBrand";

    public Post add(Post post) {
        crudRepository.run(session -> session.save(post));
        return post;
    }

    public List<Post> findAll() {
        return crudRepository.query(
                SELECT,
                Post.class
        );
    }

    public Optional<Post> findById(int id) {
        return crudRepository.optional(
                String.format("%s %s", SELECT, BY_ID),
                Post.class,
                Map.of("fId", id)
        );
    }

    public List<Post> findByLastDay() {
        return crudRepository.query(
                String.format("%s %s", SELECT, BY_LAST_DAY),
                Post.class,
                Map.of("fCreated", LocalDateTime.now().minusDays(1))
        );
    }

    public List<Post> findByAvailabilityPhoto() {
        return crudRepository.query(
                String.format("%s %s", SELECT, BY_AVAILABILITY_PHOTO),
                Post.class
                );
    }

    public List<Post> findByBrand(String brand) {
        return crudRepository.query(
                String.format("%s %s", SELECT, BY_BRAND),
                Post.class,
                Map.of("fBrand", brand)
        );
    }
}
