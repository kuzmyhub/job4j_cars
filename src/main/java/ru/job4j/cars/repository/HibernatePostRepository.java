package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class HibernatePostRepository implements PostRepository {

    private TemplateRepository hibernateTemplateRepository;

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
            "c WHERE c.brand = :fBrand";

    private static final String UPDATE = "UPDATE Post p SET";

    private static final String DESCRIPTION =
            "p.description = :fDescription WHERE p.id = :fId";

    private static final String SOLD = "p.sold = :fSold WHERE p.id = :fId";

    public Optional<Post> add(Post post) {
        Optional<Post> addingPost;
        try {
            hibernateTemplateRepository.run(session -> session.save(post));
            addingPost = Optional.of(post);
        } catch (Exception e) {
            addingPost = Optional.empty();
        }
        return addingPost;
    }

    public List<Post> findAll() {
        return hibernateTemplateRepository.query(
                SELECT,
                Post.class
        );
    }

    public Optional<Post> findById(int id) {
        return hibernateTemplateRepository.optional(
                String.format("%s %s", SELECT, BY_ID),
                Post.class,
                Map.of("fId", id)
        );
    }

    public List<Post> findByLastDay() {
        return hibernateTemplateRepository.query(
                String.format("%s %s", SELECT, BY_LAST_DAY),
                Post.class,
                Map.of("fCreated", LocalDateTime.now().minusDays(1))
        );
    }

    public List<Post> findByAvailabilityPhoto() {
        return hibernateTemplateRepository.query(
                String.format("%s %s", SELECT, BY_AVAILABILITY_PHOTO),
                Post.class
                );
    }

    public List<Post> findByBrand(String brand) {
        return hibernateTemplateRepository.query(
                String.format("%s %s", SELECT, BY_BRAND),
                Post.class,
                Map.of("fBrand", brand)
        );
    }

    public void updateDescription(int id, String description) {
        hibernateTemplateRepository.run(
                String.format("%s %s", UPDATE, DESCRIPTION),
                Map.of("fDescription", description, "fId", id
                ));
    }

    public void changeStatus(int id, boolean sold) {
        hibernateTemplateRepository.run(
                String.format("%s %s", UPDATE, SOLD),
                Map.of("fSold", sold, "fId", id
                ));
    }

    public void update(Post post) {
        hibernateTemplateRepository.run(session -> session.update(post));
    }
}
