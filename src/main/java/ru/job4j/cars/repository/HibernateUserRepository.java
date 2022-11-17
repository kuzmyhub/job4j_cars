package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {

    private TemplateRepository hibernateTemplateRepository;

    public static final String SELECT = "FROM User u";

    public static final String BY_ID = "WHERE u.id = :fId";

    public static final String BY_LOGIN_AND_PASSWORD = "WHERE u.login = :fLogin AND u.password = :fPassword";

    public Optional<User> add(User user) {
        Optional<User> addingUser;
        try {
            hibernateTemplateRepository.run(session -> session.save(user));
            addingUser = Optional.of(user);
        } catch (Exception e) {
            addingUser = Optional.empty();
        }
        return addingUser;
    }

    public Optional<User> findById(int id) {
        return hibernateTemplateRepository.optional(
                String.format("%s %s", SELECT, BY_ID),
                User.class,
                Map.of("fId", id)
        );
    }

    public Optional<User> findByLoginAndPassword(User user) {
        return hibernateTemplateRepository.optional(
                String.format("%s %s", SELECT, BY_LOGIN_AND_PASSWORD),
                User.class,
                Map.of("fLogin", user.getLogin(), "fPassword", user.getPassword())
        );
    }

    public void update(User user) {
        hibernateTemplateRepository.run(session -> session.update(user));
    }

    public Optional<User> findParticipatesByUser(int id) {
        return hibernateTemplateRepository.optional(
                "FROM User u JOIN FETCH u.participates WHERE u.id = :fId",
                User.class,
                Map.of("fId", id));
    }
}
