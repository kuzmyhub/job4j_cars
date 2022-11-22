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
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class HibernatePriceHistoryRepositoryTest {

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
    public void whenAddPriceHistoryThenSetPriceHistoryId() {
        TemplateRepository hibernateTemplateRepository =
                new HibernateTemplateRepository(sessionFactory);
        PriceHistoryRepository hibernatePriceHistoryRepository =
                new HibernatePriceHistoryRepository(hibernateTemplateRepository);
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setBefore(50000);
        priceHistory.setAfter(45000);
        hibernatePriceHistoryRepository.add(priceHistory);
        int notExpected = 0;
        assertThat(notExpected).isNotEqualTo(priceHistory.getId());
    }

}