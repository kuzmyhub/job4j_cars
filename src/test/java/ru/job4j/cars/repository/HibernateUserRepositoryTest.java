package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class HibernateUserRepositoryTest {

    private static SessionFactory sessionFactory;

    @BeforeAll
    public static void createSessionFactory() {
        StandardServiceRegistry registry =
                new StandardServiceRegistryBuilder()
                        .configure()
                        .build();
        sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }

    @AfterEach
    public void cleanTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<User> users = session.createQuery("FROM User")
                .list();
        for (User u : users) {
            u.getParticipates().clear();
            session.merge(u);
        }
        session.flush();
        session.createQuery("DELETE FROM PriceHistory")
                .executeUpdate();
        session.createQuery("DELETE FROM Post")
                .executeUpdate();
        List<Car> cars = session.createQuery("FROM Car")
                .list();
        for (Car c : cars) {
            c.getOwners().clear();
            session.merge(c);
        }
        session.flush();
        session.createQuery("DELETE FROM Driver")
                .executeUpdate();
        session.createQuery("DELETE FROM User")
                .executeUpdate();
        session.createQuery("DELETE FROM Car")
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void whenAddUserThenSetUserId() {
        TemplateRepository hibernateTemplateRepository =
                new HibernateTemplateRepository(sessionFactory);
        UserRepository hibernateUserRepository =
                new HibernateUserRepository(hibernateTemplateRepository);
        User user = new User();
        user.setLogin("user");
        user.setPassword("password");
        hibernateUserRepository.add(user);
        int notExpected = 0;
        assertThat(notExpected).isNotEqualTo(user.getId());
    }

    @Test
    public void whenAddUserThenGetSomeUserById() {
        TemplateRepository hibernateTemplateRepository =
                new HibernateTemplateRepository(sessionFactory);
        UserRepository hibernateUserRepository =
                new HibernateUserRepository(hibernateTemplateRepository);
        User user = new User();
        user.setLogin("user");
        user.setPassword("password");
        hibernateUserRepository.add(user);
        User dbUser = hibernateUserRepository
                .findById(user.getId())
                .get();
        assertThat(user.getLogin()).isEqualTo(dbUser.getLogin());
        assertThat(user.getPassword()).isEqualTo(dbUser.getPassword());
    }

    @Test
    public void whenAddUserThenGetSomeUserByLoginAndPassword() {
        TemplateRepository hibernateTemplateRepository =
                new HibernateTemplateRepository(sessionFactory);
        UserRepository hibernateUserRepository =
                new HibernateUserRepository(hibernateTemplateRepository);
        User user = new User();
        user.setLogin("user");
        user.setPassword("password");
        hibernateUserRepository.add(user);
        User dbUser = hibernateUserRepository
                .findByLoginAndPassword(user)
                .get();
        assertThat(user.getLogin()).isEqualTo(dbUser.getLogin());
        assertThat(user.getPassword()).isEqualTo(dbUser.getPassword());
    }

    @Test
    public void whenUpdateUserThenGetModified() {
        TemplateRepository hibernateTemplateRepository =
                new HibernateTemplateRepository(sessionFactory);
        UserRepository hibernateUserRepository =
                new HibernateUserRepository(hibernateTemplateRepository);
        User user = new User();
        user.setLogin("user");
        user.setPassword("password");
        hibernateUserRepository.add(user);
        String expected = "newPassword";
        user.setPassword(expected);
        hibernateUserRepository.update(user);
        User dbUser = hibernateUserRepository
                .findById(user.getId())
                .get();
        assertThat(expected).isEqualTo(dbUser.getPassword());
    }

    @Test
    public void whenAddUserWithParticipatesThenGetUserWithSameParticipates() {
        TemplateRepository hibernateTemplateRepository =
                new HibernateTemplateRepository(sessionFactory);
        UserRepository hibernateUserRepository =
                new HibernateUserRepository(hibernateTemplateRepository);
        User user = new User();
        user.setLogin("user");
        user.setPassword("password");
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setBefore(50000);
        priceHistory.setBefore(45000);
        Car car = new Car();
        car.setBrand("СМЗ");
        car.setModel("С-3Д");
        Post post = new Post();
        post.setDescription("Продам авто");
        post.setUser(user);
        post.setPriceHistories(List.of(priceHistory));
        post.setCar(car);
        user.setParticipates(List.of(post));
        hibernateUserRepository.add(user);
        User dbUser = hibernateUserRepository
                .findParticipatesByUser(user.getId())
                .get();
        assertThat(user.getParticipates().get(0))
                .isEqualTo(dbUser.getParticipates().get(0));
    }
}