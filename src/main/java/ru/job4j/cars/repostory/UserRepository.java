package ru.job4j.cars.repostory;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class UserRepository {

    private CrudRepository crudRepository;

    public static final String SELECT = "FROM User u";

    public static final String BY_ID = "WHERE u.id = :fId";

    public User add(User user) {
        crudRepository.run(session -> session.save(user));
        return user;
    }

    public Optional<User> findById(int id) {
        return crudRepository.optional(
                String.format("%s %s", SELECT, BY_ID),
                User.class,
                Map.of("fId", id)
        );
    }
}
