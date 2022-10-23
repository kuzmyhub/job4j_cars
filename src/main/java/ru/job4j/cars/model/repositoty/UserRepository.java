package ru.job4j.cars.model.repositoty;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.job4j.cars.model.User;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {
    private final SessionFactory sf;

    private Session getSession() {
        return sf.openSession();
    }

    /**
     * Сохранить в базе.
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        Session session = getSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    /**
     * Обновить в базе пользователя.
     * @param user пользователь.
     */
    public void update(User user) {
        Session session = getSession();
        try {
            session.beginTransaction();
            session.createQuery("UPDATE User SET login = :fLogin, password = :fPassword WHERE id = :fId")
                    .setParameter("fLogin", user.getLogin())
                    .setParameter("fPassword", user.getPassword())
                    .setParameter("fId", user.getId())
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    /**
     * Удалить пользователя по id.
     * @param userId ID
     */
    public void delete(int userId) {
        Session session = getSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE User WHERE id = :fId")
                    .setParameter("fId", userId)
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    /**
     * Список пользователь отсортированных по id.
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        Session session = getSession();
        List<User> list = session
                .createQuery(
                        "FROM User ORDER BY id", User.class
                )
                .list();
        session.close();
        return list;
    }

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    public Optional<User> findById(int userId) {
        Session session = getSession();
        Optional<User> user = session.createQuery(
                "FROM User u WHERE u.id = :fId", User.class
        ).setParameter("fId", userId).uniqueResultOptional();
        session.close();
        return user;
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        Session session = getSession();
        Query query = session.createQuery(
                "FROM User u WHERE u.login LIKE :fLogin", User.class
        );
        query.setParameter("fLogin", "%" + key + "%");
        List<User> list = query.getResultList();
        session.close();
        return list;
    }

    /**
     * Найти пользователя по login.
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        Session session = getSession();
        Optional<User> user = session.createQuery(
                "FROM User u WHERE u.login = :fLogin", User.class
        ).setParameter("fLogin", login).uniqueResultOptional();
        session.close();
        return user;
    }
}